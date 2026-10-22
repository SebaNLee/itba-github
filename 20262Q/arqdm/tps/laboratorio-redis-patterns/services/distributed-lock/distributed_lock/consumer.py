"""Redlock-backed consumer loop: acquire → POST /consume → release."""

from __future__ import annotations

import logging
import os
import random
import socket
import sys
import time
from collections.abc import Callable
from typing import Any

import httpx

from distributed_lock.inventory_app import resolve_product_ids
from distributed_lock.redlock import RedlockClient, connect_clients
from distributed_lock.uuid7 import new_lock_token

logger = logging.getLogger(__name__)


def _env_int(name: str, default: int) -> int:
    raw = os.environ.get(name)
    if raw is None or raw.strip() == "":
        return default
    return int(raw)


def _env_float(name: str, default: float) -> float:
    raw = os.environ.get(name)
    if raw is None or raw.strip() == "":
        return default
    return float(raw)


def _parse_urls() -> list[str]:
    raw = os.environ.get(
        "REDLOCK_VALKEY_URLS",
        "redis://valkey-1:6379/0,redis://valkey-2:6379/0,redis://valkey-3:6379/0",
    )
    return [u.strip() for u in raw.split(",") if u.strip()]


class ConsumerLogAdapter(logging.LoggerAdapter):
    def process(self, msg: str, kwargs: Any) -> tuple[str, Any]:
        cid = self.extra.get("consumer_id", "?")
        return f"[consumer_id={cid}] {msg}", kwargs


def _resolve_consumer_id(explicit: str | None) -> str:
    if explicit is not None and explicit.strip():
        return explicit.strip()
    return os.environ.get("CONSUMER_ID", "").strip() or socket.gethostname()


def run_consumer(
    *,
    consumer_id: str | None = None,
    on_unit_consumed: Callable[[dict[str, Any]], None] | None = None,
) -> int:
    """Run until 409 exhausted, MAX_ITERATIONS, or repeated lock failures. Returns exit code."""
    consumer_id_resolved = _resolve_consumer_id(consumer_id)
    log = ConsumerLogAdapter(logger, {"consumer_id": consumer_id_resolved})

    product_ids = resolve_product_ids()
    inventory_url = os.environ.get(
        "INVENTORY_URL", "http://inventory-server:8000"
    ).rstrip("/")
    lock_ttl_ms = _env_int("LOCK_TTL_MS", 10_000)
    max_iterations = _env_int("MAX_ITERATIONS", 1000)
    max_consecutive_acquire_failures = _env_int("MAX_CONSECUTIVE_ACQUIRE_FAILURES", 5)
    delay_min = _env_float("ACQUIRE_RETRY_DELAY_MS_MIN", 50.0)
    delay_max = _env_float("ACQUIRE_RETRY_DELAY_MS_MAX", 200.0)

    urls = _parse_urls()
    redis_clients = connect_clients(urls)
    redlock = RedlockClient(redis_clients)

    consume_url = f"{inventory_url}/consume"
    consecutive_acquire_failures = 0
    exhausted_products: set[str] = set()

    for _iteration in range(1, max_iterations + 1):
        candidates = [p for p in product_ids if p not in exhausted_products]
        if not candidates:
            log.info("all products exhausted; exiting")
            return 0

        product_id = random.choice(candidates)
        token = new_lock_token()
        ar = redlock.acquire(product_id, token, lock_ttl_ms)

        if not ar.success:
            consecutive_acquire_failures += 1
            if consecutive_acquire_failures >= max_consecutive_acquire_failures:
                log.warning(
                    "exiting after %s consecutive failed acquires (last product_id=%s)",
                    consecutive_acquire_failures,
                    product_id,
                )
                return 3
            _backoff_ms(delay_min, delay_max)
            continue

        consecutive_acquire_failures = 0

        if ar.validity_ms <= 0.0:
            log.warning(
                "lock validity non-positive after acquire; releasing and retrying (product_id=%s)",
                product_id,
            )
            redlock.release(product_id, token, warn_on_no_match=True)
            continue

        log.info("acquired lock product_id=%s", product_id)

        resp: httpx.Response | None = None
        try:
            try:
                with httpx.Client(timeout=30.0) as client:
                    resp = client.post(
                        consume_url,
                        json={"product_id": product_id},
                        headers={"X-Lock-Token": token},
                    )
            except httpx.RequestError as exc:
                log.error(
                    "inventory unreachable: %s (product_id=%s)",
                    exc,
                    product_id,
                )
        finally:
            redlock.release(product_id, token, warn_on_no_match=True)

        if resp is None:
            continue

        if resp.status_code == 409:
            exhausted_products.add(product_id)
            log.info("exhausted out of stock (product_id=%s)", product_id)
            continue

        if resp.status_code == 200:
            data = resp.json()
            remaining = data.get("remaining")
            log.info(
                "consumed product_id=%s remaining=%s",
                product_id,
                remaining,
            )
            if on_unit_consumed is not None:
                on_unit_consumed(dict(data))
            time.sleep(random.uniform(0.0, 1.0))
            continue

        log.warning(
            "unexpected status %s from inventory: %s",
            resp.status_code,
            resp.text[:500],
        )

    log.warning("MAX_ITERATIONS reached; exiting without exhausting stock")
    return 2


def _backoff_ms(delay_min: float, delay_max: float) -> None:
    ms = random.uniform(delay_min, delay_max)
    time.sleep(ms / 1000.0)

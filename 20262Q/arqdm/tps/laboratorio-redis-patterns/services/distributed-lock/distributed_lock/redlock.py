"""Hand-rolled Redlock (N=3, quorum=2) over independent Valkey/Redis masters."""

from __future__ import annotations

import logging
import time
from concurrent.futures import ThreadPoolExecutor
from dataclasses import dataclass
from typing import Sequence

import redis

from distributed_lock.lock_key import inventory_lock_key

logger = logging.getLogger(__name__)

# Original Redlock clock drift margin (milliseconds).
_CLOCK_DRIFT_FACTOR = 0.01
_CLOCK_DRIFT_MIN_MS = 2.0

# Atomic compare-and-delete: returns 1 if key deleted, 0 if token mismatch or missing.
RELEASE_LUA = """
if redis.call("GET", KEYS[1]) == ARGV[1] then
  return redis.call("DEL", KEYS[1])
else
  return 0
end
"""


@dataclass(frozen=True)
class AcquireResult:
    success: bool
    token: str
    """Same token passed in; included for clarity at call sites."""
    validity_ms: float
    succeeded_nodes: frozenset[int]
    """Indices of Redis clients where SET NX succeeded for this attempt."""


class RedlockClient:
    """Redlock client: SET NX PX per node; Lua release; quorum acquire."""

    def __init__(
        self,
        clients: Sequence[redis.Redis],
        *,
        quorum: int | None = None,
    ) -> None:
        if len(clients) != 3:
            msg = f"expected exactly 3 Valkey masters, got {len(clients)}"
            raise ValueError(msg)
        self._clients = tuple(clients)
        self._quorum = quorum if quorum is not None else len(clients) // 2 + 1

    def lock_key(self, product_id: str) -> str:
        return inventory_lock_key(product_id)

    def acquire(
        self,
        product_id: str,
        token: str,
        ttl_ms: int,
    ) -> AcquireResult:
        key = self.lock_key(product_id)
        start = time.time() * 1000.0

        def try_set(idx: int) -> tuple[int, bool]:
            ok = bool(self._clients[idx].set(name=key, value=token, nx=True, px=ttl_ms))
            return idx, ok

        succeeded: set[int] = set()
        with ThreadPoolExecutor(max_workers=3) as pool:
            futures = [pool.submit(try_set, i) for i in range(3)]
            for f in futures:
                idx, ok = f.result()
                if ok:
                    succeeded.add(idx)

        elapsed = time.time() * 1000.0 - start
        drift = ttl_ms * _CLOCK_DRIFT_FACTOR + _CLOCK_DRIFT_MIN_MS
        validity_ms = ttl_ms - elapsed - drift

        if len(succeeded) < self._quorum:
            self._release_partial(key, token, succeeded)
            return AcquireResult(
                success=False,
                token=token,
                validity_ms=0.0,
                succeeded_nodes=frozenset(succeeded),
            )

        return AcquireResult(
            success=True,
            token=token,
            validity_ms=validity_ms,
            succeeded_nodes=frozenset(succeeded),
        )

    def _release_partial(self, key: str, token: str, indices: set[int]) -> None:
        for idx in indices:
            try:
                self._clients[idx].eval(RELEASE_LUA, 1, key, token)
            except redis.RedisError:
                logger.exception("partial release failed on node %s", idx)

    def release(
        self, product_id: str, token: str, *, warn_on_no_match: bool = True
    ) -> None:
        key = self.lock_key(product_id)
        for idx, client in enumerate(self._clients):
            try:
                n = int(client.eval(RELEASE_LUA, 1, key, token))
                if n == 0 and warn_on_no_match:
                    logger.warning(
                        "release no-match (token mismatch or expired) on node %s for key=%s",
                        idx,
                        key,
                    )
            except redis.RedisError:
                logger.exception("release failed on node %s", idx)


def connect_clients(urls: list[str]) -> list[redis.Redis]:
    return [redis.Redis.from_url(u, decode_responses=True) for u in urls]

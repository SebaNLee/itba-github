"""Distributed lock lab: Valkey primitives, Lua release, and Redlock smoke (T-01–T-04)."""

from __future__ import annotations

import socket
import threading
import time
from collections.abc import Iterator
from contextlib import ExitStack
from typing import Any

import httpx
import pytest
import uvicorn
from testcontainers.redis import RedisContainer

try:
    import docker

    _docker_ok = bool(docker.from_env().ping())
except Exception:  # noqa: BLE001 — probe only
    _docker_ok = False


@pytest.fixture(scope="module")
def _require_docker() -> None:
    if not _docker_ok:
        pytest.skip("Docker daemon not available (required for Valkey testcontainers)")


from distributed_lock.consumer import run_consumer
from distributed_lock.inventory_app import create_app
from distributed_lock.lock_key import inventory_lock_key
from distributed_lock.redlock import RELEASE_LUA, RedlockClient, connect_clients
from distributed_lock.uuid7 import new_lock_token


def _valkey_url(container: RedisContainer) -> str:
    return (
        f"redis://{container.get_container_host_ip()}:"
        f"{container.get_exposed_port(container.port)}/0"
    )


@pytest.fixture
def single_valkey(_require_docker: None) -> Iterator[RedisContainer]:
    """Given: one Valkey container for per-node tests."""
    with RedisContainer("valkey/valkey:7.2") as c:
        yield c


@pytest.fixture
def three_valkeys(_require_docker: None) -> Iterator[list[RedisContainer]]:
    """Given: three independent Valkey instances for Redlock integration."""
    with ExitStack() as stack:
        nodes: list[RedisContainer] = []
        for _ in range(3):
            c = RedisContainer("valkey/valkey:7.2")
            stack.enter_context(c)
            nodes.append(c)
        yield nodes


def test_t01_mutual_exclusion(single_valkey: RedisContainer) -> None:
    """T-01: second SET NX fails while first holder's token remains."""
    # Given
    client = single_valkey.get_client(decode_responses=True)
    key = inventory_lock_key("flash-sale-001")
    token_a = new_lock_token()
    token_b = new_lock_token()
    ttl_ms = 10_000

    # When
    first = client.set(key, token_a, nx=True, px=ttl_ms)
    second = client.set(key, token_b, nx=True, px=ttl_ms)

    # Then (redis-py: failed NX SET returns None, not False)
    assert first is True
    assert second is None
    assert client.get(key) == token_a

    # Clean
    client.delete(key)


def test_t02_wrong_token_release_is_noop(single_valkey: RedisContainer) -> None:
    """T-02: Lua release with wrong token does not delete; value unchanged."""
    # Given
    client = single_valkey.get_client(decode_responses=True)
    key = inventory_lock_key("flash-sale-001")
    token_a = new_lock_token()
    token_b = new_lock_token()
    client.set(key, token_a, nx=True, px=10_000)

    # When
    n = int(client.eval(RELEASE_LUA, 1, key, token_b))

    # Then
    assert n == 0
    assert client.get(key) == token_a

    # Clean
    client.delete(key)


def test_t03_correct_token_release_succeeds(single_valkey: RedisContainer) -> None:
    """T-03: Lua release with correct token deletes the key."""
    # Given
    client = single_valkey.get_client(decode_responses=True)
    key = inventory_lock_key("flash-sale-001")
    token_a = new_lock_token()
    client.set(key, token_a, nx=True, px=10_000)

    # When
    n = int(client.eval(RELEASE_LUA, 1, key, token_a))

    # Then
    assert n == 1
    assert client.exists(key) == 0


def test_t04_smoke_no_oversell(
    three_valkeys: list[RedisContainer],
    monkeypatch: pytest.MonkeyPatch,
) -> None:
    """T-04: three Valkey nodes + inventory; concurrent consumers sell exactly INITIAL_STOCK."""
    # Given
    monkeypatch.setenv("INITIAL_STOCK", "10")
    monkeypatch.setenv("PRODUCT_ID", "flash-sale-001")
    urls = ",".join(_valkey_url(c) for c in three_valkeys)
    monkeypatch.setenv("REDLOCK_VALKEY_URLS", urls)
    monkeypatch.setenv("MAX_ITERATIONS", "20000")
    monkeypatch.setenv("LOCK_TTL_MS", "10000")

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(("127.0.0.1", 0))
    port = int(sock.getsockname()[1])
    sock.close()

    def _serve() -> None:
        uvicorn.run(
            create_app(),
            host="127.0.0.1",
            port=port,
            workers=1,
            log_level="error",
        )

    th = threading.Thread(target=_serve, daemon=True)
    th.start()
    base = f"http://127.0.0.1:{port}"
    for _ in range(100):
        try:
            r = httpx.get(f"{base}/health", timeout=0.2)
            if r.status_code == 200:
                break
        except httpx.HTTPError:
            time.sleep(0.05)
    else:
        pytest.fail("inventory server did not become ready")

    monkeypatch.setenv("INVENTORY_URL", base)

    events: list[dict[str, Any]] = []
    lock = threading.Lock()

    def _worker(cid: str) -> None:
        def _on_unit(data: dict[str, Any]) -> None:
            with lock:
                events.append({"consumer_id": cid, **data})

        run_consumer(consumer_id=cid, on_unit_consumed=_on_unit)

    threads = [
        threading.Thread(target=_worker, args=("c-alpha",)),
        threading.Thread(target=_worker, args=("c-beta",)),
        threading.Thread(target=_worker, args=("c-gamma",)),
    ]
    # When
    for t in threads:
        t.start()
    for t in threads:
        t.join(timeout=120)

    # Then
    for t in threads:
        assert not t.is_alive(), "consumer thread should finish"

    assert len(events) == 10
    r = httpx.post(
        f"{base}/consume",
        json={"product_id": "flash-sale-001"},
        timeout=5.0,
    )
    assert r.status_code == 409

    ids = {e["consumer_id"] for e in events}
    assert len(ids) >= 2


def test_package_exposes_version() -> None:
    import distributed_lock

    assert isinstance(distributed_lock.__version__, str)
    assert distributed_lock.__version__

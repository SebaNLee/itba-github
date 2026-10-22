"""Tests for the cache-aside lab (spec §6). Plain functions; Given/When/Then/Clean."""

from __future__ import annotations

import json
import os
import time
from dataclasses import replace
from unittest.mock import AsyncMock, patch

import pytest
import redis
from starlette.testclient import TestClient

from redis_cache_aside import __version__
from redis_cache_aside.asgi import build_app
from redis_cache_aside.metrics import get_metrics
from redis_cache_aside.product_model import (
    NEGATIVE_CACHE_SENTINEL,
    CacheParse,
    ProductRow,
    parse_product_cache_value,
    product_cache_key,
    product_row_to_json,
)
from redis_cache_aside.settings import Settings, load_settings

PRODUCT_QUERY = """
query Product($id: ID!) {
  product(id: $id) {
    id
    title
    description
    price
  }
}
"""

integration = pytest.mark.skipif(
    not os.environ.get("DATABASE_URL"),
    reason="DATABASE_URL not set (run: docker compose run --rm test)",
)


def test_version_is_defined() -> None:
    # Given
    expected = "0.1.0"
    # When
    got = __version__
    # Then
    assert got == expected
    # Clean — no shared state


def test_product_cache_key_is_normative() -> None:
    # Given
    pid = "demo-1"
    # When
    key = product_cache_key(pid)
    # Then
    assert key == "catalog:product:demo-1"
    # Clean


def test_negative_sentinel_roundtrip() -> None:
    # Given
    raw = json.dumps(NEGATIVE_CACHE_SENTINEL, separators=(",", ":"))
    # When
    parsed = parse_product_cache_value(raw)
    # Then
    assert parsed is CacheParse.ABSENT
    # Clean


@pytest.mark.asyncio
async def test_ttl_mechanism_b_mock_expiry_triggers_reload() -> None:
    """Mechanism B (spec §6.1): simulated expiry via Redis GET returning miss after a hit."""
    # Given
    get_metrics().reset()
    row = ProductRow(id="unit-x", title="t", description="d", price=3.0)
    payload = product_row_to_json(row).encode()
    redis = AsyncMock()
    redis.get = AsyncMock(side_effect=[None, payload, None])
    redis.set = AsyncMock()
    pool = AsyncMock()
    settings = Settings(
        database_url="postgresql://unused",
        redis_url="redis://unused",
        cache_ttl_seconds=60,
        cache_enabled=True,
        host="0.0.0.0",
        port=8000,
    )
    from redis_cache_aside.catalog_service import CatalogService

    with patch(
        "redis_cache_aside.catalog_service.fetch_product", new_callable=AsyncMock
    ) as mock_fetch:
        mock_fetch.return_value = row
        svc = CatalogService(pool=pool, redis=redis, settings=settings)

        # When
        first = await svc.get_product("unit-x")
        second = await svc.get_product("unit-x")
        third = await svc.get_product("unit-x")

    # Then
    assert first == row
    assert second == row
    assert third == row
    assert get_metrics().db_reads == 2
    assert mock_fetch.await_count == 2
    assert redis.get.await_count == 3
    # Clean
    get_metrics().reset()


@integration
def test_integration_cold_miss_then_warm_hit() -> None:
    # Given
    r = redis.from_url(os.environ["REDIS_URL"], decode_responses=False)
    key = product_cache_key("demo-1")
    r.delete(key)
    get_metrics().reset()
    app = build_app()
    # When
    with TestClient(app) as client:
        body = {"query": PRODUCT_QUERY, "variables": {"id": "demo-1"}}
        res1 = client.post("/graphql", json=body)
        res2 = client.post("/graphql", json=body)
    # Then
    assert res1.status_code == 200
    assert res2.status_code == 200
    p1 = res1.json()["data"]["product"]
    p2 = res2.json()["data"]["product"]
    assert p1["id"] == "demo-1"
    assert p2["id"] == "demo-1"
    assert get_metrics().cache_misses >= 1
    assert get_metrics().cache_hits >= 1
    assert get_metrics().db_reads == 1
    # Clean
    r.delete(key)
    get_metrics().reset()


@integration
def test_integration_unknown_id_negative_cache() -> None:
    # Given
    r = redis.from_url(os.environ["REDIS_URL"], decode_responses=False)
    missing_id = "no-such-product-integration"
    key = product_cache_key(missing_id)
    r.delete(key)
    get_metrics().reset()
    app = build_app()
    body = {"query": PRODUCT_QUERY, "variables": {"id": missing_id}}
    # When
    with TestClient(app) as client:
        res1 = client.post("/graphql", json=body)
        db_after_first = get_metrics().db_reads
        res2 = client.post("/graphql", json=body)
        db_after_second = get_metrics().db_reads
    # Then
    assert res1.status_code == 200
    assert res2.status_code == 200
    assert res1.json()["data"]["product"] is None
    assert res2.json()["data"]["product"] is None
    assert db_after_first == 1
    assert db_after_second == 1
    cached = r.get(key)
    assert cached is not None
    assert parse_product_cache_value(cached.decode()) is CacheParse.ABSENT
    # Clean
    r.delete(key)
    get_metrics().reset()


@integration
def test_integration_ttl_mechanism_a_short_ttl_and_sleep() -> None:
    """Mechanism A (spec §6.1): CACHE_TTL_SECONDS=2 in Compose + sleep + real Valkey."""
    # Given
    r = redis.from_url(os.environ["REDIS_URL"], decode_responses=False)
    key = product_cache_key("demo-1")
    r.delete(key)
    get_metrics().reset()
    app = build_app()
    body = {"query": PRODUCT_QUERY, "variables": {"id": "demo-1"}}
    # When
    with TestClient(app) as client:
        client.post("/graphql", json=body)
        client.post("/graphql", json=body)
        mid_reads = get_metrics().db_reads
        time.sleep(float(os.environ.get("CACHE_TTL_SECONDS", "2")) + 0.6)
        client.post("/graphql", json=body)
        final_reads = get_metrics().db_reads
    # Then
    assert mid_reads == 1
    assert final_reads == 2
    # Clean
    r.delete(key)
    get_metrics().reset()


@integration
def test_integration_graphql_minimum_fields() -> None:
    # Given
    app = build_app()
    # When
    with TestClient(app) as client:
        res = client.post(
            "/graphql",
            json={"query": PRODUCT_QUERY, "variables": {"id": "hot-sku"}},
        )
    # Then
    assert res.status_code == 200
    payload = res.json()["data"]["product"]
    assert payload["title"] == "Hot SKU"
    assert payload["description"]
    assert payload["price"] == 49.0
    # Clean
    get_metrics().reset()


@integration
def test_integration_valkey_unavailable_falls_back_to_postgres() -> None:
    # Given
    base = load_settings()
    bad = replace(base, redis_url="redis://127.0.0.1:1")
    app = build_app(bad)
    get_metrics().reset()
    body = {"query": PRODUCT_QUERY, "variables": {"id": "demo-1"}}
    # When
    with TestClient(app) as client:
        res = client.post("/graphql", json=body)
    # Then
    assert res.status_code == 200
    p = res.json()["data"]["product"]
    assert p["id"] == "demo-1"
    assert get_metrics().db_reads >= 1
    # Clean
    get_metrics().reset()

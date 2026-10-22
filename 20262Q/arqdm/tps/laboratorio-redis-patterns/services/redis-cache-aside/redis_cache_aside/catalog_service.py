from __future__ import annotations

import asyncpg
from redis.asyncio import Redis

from redis_cache_aside.db import fetch_product
from redis_cache_aside.metrics import get_metrics
from redis_cache_aside.product_model import (
    NEGATIVE_CACHE_JSON,
    CacheParse,
    ProductRow,
    parse_product_cache_value,
    product_cache_key,
    product_row_to_json,
)
from redis_cache_aside.settings import Settings


class CatalogService:
    """Cache-aside for single-product reads (spec §3.1)."""

    def __init__(
        self,
        *,
        pool: asyncpg.Pool,
        redis: Redis,
        settings: Settings,
    ) -> None:
        self._pool = pool
        self._redis = redis
        self._settings = settings
        self._metrics = get_metrics()

    async def get_product(self, product_id: str) -> ProductRow | None:
        key = product_cache_key(product_id)
        ttl = self._settings.cache_ttl_seconds

        if not self._settings.cache_enabled:
            self._metrics.record_miss()
            row = await self._load_from_db(product_id)
            return row

        raw = await self._cache_get(key)
        if raw is not None:
            try:
                text = raw.decode()
            except UnicodeDecodeError:
                text = ""
            parsed = parse_product_cache_value(text)
            if parsed is CacheParse.ABSENT:
                self._metrics.record_hit()
                return None
            if isinstance(parsed, ProductRow):
                self._metrics.record_hit()
                return parsed

        self._metrics.record_miss()
        row = await self._load_from_db(product_id)
        if row is not None:
            await self._cache_set(key, product_row_to_json(row).encode(), ttl)
            return row

        await self._cache_set(key, NEGATIVE_CACHE_JSON.encode(), ttl)
        return None

    async def _load_from_db(self, product_id: str) -> ProductRow | None:
        self._metrics.record_db_read()
        return await fetch_product(self._pool, product_id)

    async def _cache_get(self, key: str) -> bytes | None:
        try:
            return await self._redis.get(key)
        except Exception:
            return None

    async def _cache_set(self, key: str, value: bytes, ttl: int) -> None:
        try:
            await self._redis.set(key, value, ex=ttl)
        except Exception:
            self._metrics.record_cache_write_failure()

"""
Demo CLI (spec section 5): compare catalog reads with CACHE_ENABLED=false vs true.

The ~80% reduction in database read load for skewed hot keys is a design goal for
realistic traffic; printed numbers illustrate the mechanism, not a production SLA.
"""

from __future__ import annotations

import asyncio
from dataclasses import replace

import redis.asyncio as aioredis

from redis_cache_aside.catalog_service import CatalogService
from redis_cache_aside.db import create_pool
from redis_cache_aside.metrics import get_metrics
from redis_cache_aside.settings import load_settings


async def _run_async() -> None:
    settings = load_settings()
    get_metrics().reset()
    pool = await create_pool(settings.database_url)
    redis = aioredis.from_url(settings.redis_url, decode_responses=False)
    product_id = "demo-1"
    iterations = 20
    try:
        await redis.flushdb()
        get_metrics().reset()
        cold = replace(settings, cache_enabled=False)
        svc_off = CatalogService(pool=pool, redis=redis, settings=cold)
        for _ in range(iterations):
            await svc_off.get_product(product_id)
        off = get_metrics().snapshot()

        await redis.flushdb()
        get_metrics().reset()
        svc_on = CatalogService(pool=pool, redis=redis, settings=settings)
        for _ in range(iterations):
            await svc_on.get_product(product_id)
        on = get_metrics().snapshot()
    finally:
        await redis.aclose()
        await pool.close()

    print("redis-cache-aside demo (spec section 5)")
    print(f"Product id={product_id!r}, iterations={iterations}")
    print()
    print("CACHE_ENABLED=false (every read loads the row):")
    print(
        f"  db_reads={off.db_reads} cache_hits={off.cache_hits} cache_misses={off.cache_misses}"
    )
    print()
    print("CACHE_ENABLED=true (cache-aside against Valkey):")
    print(
        f"  db_reads={on.db_reads} cache_hits={on.cache_hits} cache_misses={on.cache_misses}"
    )
    if on.cache_hits + on.cache_misses:
        ratio = on.cache_hits / (on.cache_hits + on.cache_misses)
        print(f"  hit_ratio={ratio:.3f}")
    print()
    print(
        "In skewed real traffic, a hot key can drop repeated DB reads by a large factor; "
        "this script shows one warm key after a cold miss (spec sections 2.1 and 5.3)."
    )


def main() -> None:
    asyncio.run(_run_async())


if __name__ == "__main__":
    main()

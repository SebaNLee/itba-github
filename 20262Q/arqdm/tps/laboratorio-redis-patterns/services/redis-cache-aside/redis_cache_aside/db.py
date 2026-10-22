from __future__ import annotations

import asyncpg

from redis_cache_aside.product_model import ProductRow


async def create_pool(dsn: str) -> asyncpg.Pool:
    return await asyncpg.create_pool(dsn, min_size=1, max_size=10)


async def fetch_product(pool: asyncpg.Pool, product_id: str) -> ProductRow | None:
    async with pool.acquire() as conn:
        row = await conn.fetchrow(
            """
            SELECT id, title, description, price
            FROM products
            WHERE id = $1
            """,
            product_id,
        )
    if row is None:
        return None
    return ProductRow(
        id=row["id"],
        title=row["title"],
        description=row["description"],
        price=float(row["price"]),
    )

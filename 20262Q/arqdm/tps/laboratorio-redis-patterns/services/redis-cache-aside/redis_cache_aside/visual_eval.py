"""
Visual evaluation CLI (specs/02-cli-eval.md): heavy GraphQL consumer, cache on vs off.

Runs inside Docker Compose service `eval`; not a pytest suite.
"""

from __future__ import annotations

import argparse
import asyncio
import os
import time
from dataclasses import replace

import httpx
import redis.asyncio as aioredis
from asgi_lifespan import LifespanManager
from httpx import ASGITransport
from rich.console import Console
from rich.panel import Panel
from rich.table import Table

from redis_cache_aside.asgi import build_app
from redis_cache_aside.metrics import MetricsSnapshot, get_metrics
from redis_cache_aside.settings import load_settings

PRODUCT_QUERY = """
query ProductEval($id: ID!) {
  product(id: $id) {
    id
    title
    description
    price
  }
}
"""


async def _flush_redis(redis_url: str) -> None:
    r = aioredis.from_url(redis_url, decode_responses=False)
    try:
        await r.flushdb()
    finally:
        await r.aclose()


async def _run_phase(
    settings,
    *,
    product_id: str,
    iterations: int,
    redis_url: str,
    concurrent: int,
) -> tuple[float, MetricsSnapshot]:
    await _flush_redis(redis_url)
    get_metrics().reset()
    app = build_app(settings)
    t0 = time.perf_counter()
    async with LifespanManager(app):
        transport = ASGITransport(app=app)
        async with httpx.AsyncClient(
            transport=transport,
            base_url="http://eval",
            follow_redirects=True,
        ) as client:

            async def one() -> None:
                resp = await client.post(
                    "/graphql/",
                    json={"query": PRODUCT_QUERY, "variables": {"id": product_id}},
                )
                resp.raise_for_status()
                body = resp.json()
                if body.get("errors"):
                    raise RuntimeError(body["errors"])

            if concurrent <= 1:
                for _ in range(iterations):
                    await one()
            else:
                sem = asyncio.Semaphore(concurrent)

                async def bounded() -> None:
                    async with sem:
                        await one()

                await asyncio.gather(*(bounded() for _ in range(iterations)))

    elapsed = time.perf_counter() - t0
    return elapsed, get_metrics().snapshot()


def _hit_ratio(s: MetricsSnapshot) -> float | None:
    total = s.cache_hits + s.cache_misses
    if not total:
        return None
    return s.cache_hits / total


def _format_ratio(x: float | None) -> str:
    if x is None:
        return "—"
    return f"{x:.1%}"


async def _run_async(
    *,
    product_id: str,
    iterations: int,
    concurrent: int,
) -> None:
    base = load_settings()
    off = replace(base, cache_enabled=False)
    on = replace(base, cache_enabled=True)

    t_off, snap_off = await _run_phase(
        off,
        product_id=product_id,
        iterations=iterations,
        redis_url=base.redis_url,
        concurrent=concurrent,
    )
    t_on, snap_on = await _run_phase(
        on,
        product_id=product_id,
        iterations=iterations,
        redis_url=base.redis_url,
        concurrent=concurrent,
    )

    console = Console()
    title = "Cache-aside visual eval — heavy consumer (GraphQL → extension → CatalogService)"
    console.print(Panel.fit(title, border_style="cyan"))

    table = Table(show_header=True, header_style="bold")
    table.add_column("Mode")
    table.add_column("Wall time (s)", justify="right")
    table.add_column("DB reads", justify="right")
    table.add_column("Cache hits", justify="right")
    table.add_column("Cache misses", justify="right")
    table.add_column("Hit ratio", justify="right")
    table.add_row(
        "Cache off (CACHE_ENABLED=false)",
        f"{t_off:.3f}",
        str(snap_off.db_reads),
        str(snap_off.cache_hits),
        str(snap_off.cache_misses),
        _format_ratio(_hit_ratio(snap_off)),
    )
    table.add_row(
        "Cache on (cache-aside)",
        f"{t_on:.3f}",
        str(snap_on.db_reads),
        str(snap_on.cache_hits),
        str(snap_on.cache_misses),
        _format_ratio(_hit_ratio(snap_on)),
    )
    console.print(table)

    console.print()
    console.print(
        "[dim]Sequential mode (default): expect DB reads ≈ iterations with cache off; "
        "with cache on, expect 1 miss + (iterations−1) hits for a hot key after flush.[/dim]"
    )
    console.print(
        "[dim]Concurrent mode: several coroutines may miss together before the first SET — "
        "DB reads can be >1; still compare totals to the cache-off run.[/dim]"
    )
    console.print(
        "[dim]~80% DB reduction for skewed traffic is a scenario goal (spec §2.1 / §5.3); "
        "numbers illustrate the mechanism, not a production SLA.[/dim]"
    )


def main() -> None:
    parser = argparse.ArgumentParser(
        description="Visual cache-aside evaluation (container)."
    )
    parser.add_argument(
        "--product-id",
        default=os.environ.get("VISUAL_EVAL_PRODUCT_ID", "demo-1"),
        help="GraphQL product(id) argument (default: env VISUAL_EVAL_PRODUCT_ID or demo-1).",
    )
    parser.add_argument(
        "--iterations",
        type=int,
        default=int(os.environ.get("VISUAL_EVAL_REQUESTS", "1000")),
        help="Number of GraphQL requests (default: env VISUAL_EVAL_REQUESTS or 1000).",
    )
    parser.add_argument(
        "--concurrent",
        type=int,
        default=int(os.environ.get("VISUAL_EVAL_CONCURRENT", "1")),
        help="Max in-flight requests (1 = sequential). Default VISUAL_EVAL_CONCURRENT or 1.",
    )
    args = parser.parse_args()
    if args.iterations < 1:
        raise SystemExit("--iterations must be >= 1")
    if args.concurrent < 1:
        raise SystemExit("--concurrent must be >= 1")
    asyncio.run(
        _run_async(
            product_id=args.product_id,
            iterations=args.iterations,
            concurrent=args.concurrent,
        )
    )


if __name__ == "__main__":
    main()

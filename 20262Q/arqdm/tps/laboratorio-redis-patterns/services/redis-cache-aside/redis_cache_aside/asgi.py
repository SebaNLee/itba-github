from __future__ import annotations

from contextlib import asynccontextmanager
from typing import Any

import redis.asyncio as aioredis
from starlette.applications import Starlette
from starlette.requests import Request
from starlette.responses import JSONResponse
from starlette.routing import Mount, Route
from strawberry.asgi import GraphQL

from redis_cache_aside.catalog_service import CatalogService
from redis_cache_aside.db import create_pool
from redis_cache_aside.graphql_schema import schema
from redis_cache_aside.metrics import get_metrics
from redis_cache_aside.settings import Settings, load_settings


async def metrics_endpoint(_: Request) -> JSONResponse:
    return JSONResponse(get_metrics().as_dict())


def build_app(settings: Settings | None = None) -> Starlette:
    resolved = settings or load_settings()
    catalog_context: dict[str, Any] = {}

    class CatalogGraphQL(GraphQL):
        async def get_context(self, request: Request, response):  # type: ignore[override]
            ctx = await super().get_context(request, response)
            ctx["catalog"] = catalog_context["catalog"]
            return ctx

    graphql_app = CatalogGraphQL(schema, graphql_ide=True)

    @asynccontextmanager
    async def app_lifespan(app: Starlette):
        pool = await create_pool(resolved.database_url)
        redis = aioredis.from_url(resolved.redis_url, decode_responses=False)
        catalog = CatalogService(pool=pool, redis=redis, settings=resolved)
        catalog_context["catalog"] = catalog
        app.state.pool = pool
        app.state.redis = redis
        app.state.catalog = catalog
        yield
        await redis.aclose()
        await pool.close()

    app = Starlette(
        routes=[
            Route("/metrics", metrics_endpoint, methods=["GET"]),
            Mount("/graphql", graphql_app),
        ],
        lifespan=app_lifespan,
    )
    app.state.settings = resolved
    return app

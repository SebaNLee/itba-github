"""In-memory inventory HTTP API (FastAPI)."""

from __future__ import annotations

import logging
import os
from typing import Annotated

from fastapi import FastAPI, Header, HTTPException
from fastapi.exceptions import RequestValidationError
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field

logger = logging.getLogger(__name__)

DEFAULT_PRODUCT_ID_PREFIX = "flash-sale"
DEFAULT_PRODUCT_COUNT = 5


def resolve_product_ids() -> list[str]:
    """IDs from PRODUCT_IDS, else single PRODUCT_ID, else flash-sale-001..005."""
    raw = os.environ.get("PRODUCT_IDS", "").strip()
    if raw:
        return [p.strip() for p in raw.split(",") if p.strip()]
    single = os.environ.get("PRODUCT_ID", "").strip()
    if single:
        return [single]
    return [
        f"{DEFAULT_PRODUCT_ID_PREFIX}-{i:03d}"
        for i in range(1, DEFAULT_PRODUCT_COUNT + 1)
    ]


class ConsumeBody(BaseModel):
    product_id: str = Field(..., min_length=1)


def _env_int(name: str, default: int) -> int:
    raw = os.environ.get(name)
    if raw is None or raw.strip() == "":
        return default
    return int(raw)


def create_app() -> FastAPI:
    product_ids = resolve_product_ids()
    initial_stock = _env_int("INITIAL_STOCK", 100)

    inventory: dict[str, int] = {pid: initial_stock for pid in product_ids}
    initial_by_product: dict[str, int] = {pid: initial_stock for pid in product_ids}

    app = FastAPI(title="Flash Sale Inventory Guard")

    @app.exception_handler(RequestValidationError)
    async def validation_error(
        _request: object,
        exc: RequestValidationError,
    ) -> JSONResponse:
        return JSONResponse(
            status_code=422,
            content={
                "error": exc.errors()[0]["msg"] if exc.errors() else "validation error"
            },
        )

    @app.get("/health")
    def health() -> dict[str, str]:
        return {"status": "ok"}

    @app.post("/consume")
    def consume(
        body: ConsumeBody,
        x_lock_token: Annotated[str | None, Header(alias="X-Lock-Token")] = None,
    ) -> dict:
        pid = body.product_id.strip() if isinstance(body.product_id, str) else ""
        if not pid:
            return JSONResponse(
                status_code=422,
                content={"error": "product_id must be a non-empty string"},
            )

        if pid not in inventory:
            return JSONResponse(
                status_code=404,
                content={"error": f"unknown product_id: {pid!r}"},
            )

        remaining = inventory[pid]
        if remaining <= 0:
            logger.info(
                "exhausted product_id=%s units_sold=%s",
                pid,
                initial_by_product.get(pid, initial_stock),
            )
            return JSONResponse(
                status_code=409,
                content={
                    "product_id": pid,
                    "remaining": 0,
                    "exhausted": True,
                },
            )

        inventory[pid] = remaining - 1
        new_remaining = inventory[pid]
        token_part = f" lock_token={x_lock_token}" if x_lock_token else ""
        logger.info(
            "consumed product_id=%s remaining=%s%s",
            pid,
            new_remaining,
            token_part,
        )
        if new_remaining == 0:
            logger.info(
                "exhausted product_id=%s units_sold=%s",
                pid,
                initial_by_product.get(pid, initial_stock),
            )

        return {"product_id": pid, "remaining": new_remaining}

    return app

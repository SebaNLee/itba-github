from __future__ import annotations

import json
from dataclasses import dataclass
from enum import Enum, auto
from typing import Any

# Normative key shape (spec §3.2): catalog:product:{id}
PRODUCT_CACHE_KEY_PREFIX = "catalog:product:"


def product_cache_key(product_id: str) -> str:
    return f"{PRODUCT_CACHE_KEY_PREFIX}{product_id}"


# Documented absent sentinel (spec §3.6). MUST be distinguishable from a real Product JSON object.
NEGATIVE_CACHE_SENTINEL: dict[str, Any] = {"_absent": True}
NEGATIVE_CACHE_JSON = json.dumps(NEGATIVE_CACHE_SENTINEL, separators=(",", ":"))


@dataclass(frozen=True)
class ProductRow:
    id: str
    title: str
    description: str
    price: float


def product_row_to_json(row: ProductRow) -> str:
    payload = {
        "id": row.id,
        "title": row.title,
        "description": row.description,
        "price": row.price,
    }
    return json.dumps(payload, separators=(",", ":"))


class CacheParse(Enum):
    """Outcome of interpreting a cached JSON string under catalog:product:{id}."""

    MISS = auto()
    ABSENT = auto()


def parse_product_cache_value(raw: str) -> ProductRow | CacheParse:
    try:
        data: Any = json.loads(raw)
    except (json.JSONDecodeError, TypeError):
        return CacheParse.MISS
    if data == NEGATIVE_CACHE_SENTINEL:
        return CacheParse.ABSENT
    if not isinstance(data, dict):
        return CacheParse.MISS
    try:
        return ProductRow(
            id=str(data["id"]),
            title=str(data["title"]),
            description=str(data["description"]),
            price=float(data["price"]),
        )
    except (KeyError, TypeError, ValueError):
        return CacheParse.MISS

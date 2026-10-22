"""Lock key format: lock:inventory:{product_id}."""

from __future__ import annotations


def inventory_lock_key(product_id: str) -> str:
    if not product_id or not product_id.strip():
        msg = "product_id must be a non-empty string"
        raise ValueError(msg)
    return f"lock:inventory:{product_id.strip()}"

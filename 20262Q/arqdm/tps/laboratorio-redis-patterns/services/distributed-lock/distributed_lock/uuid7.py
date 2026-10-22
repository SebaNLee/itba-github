"""UUID v7 token generation (RFC 9562)."""

from __future__ import annotations

from uuid6 import uuid7 as _uuid7


def new_lock_token() -> str:
    return str(_uuid7())

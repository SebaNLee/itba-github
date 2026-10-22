from __future__ import annotations

import os
from dataclasses import dataclass


def _truthy(name: str, default: str = "true") -> bool:
    return os.environ.get(name, default).lower() in ("1", "true", "yes", "on")


@dataclass(frozen=True)
class Settings:
    database_url: str
    redis_url: str
    cache_ttl_seconds: int
    cache_enabled: bool
    host: str
    port: int


def load_settings() -> Settings:
    return Settings(
        database_url=os.environ["DATABASE_URL"],
        redis_url=os.environ.get("REDIS_URL", "redis://127.0.0.1:6379/0"),
        cache_ttl_seconds=int(os.environ.get("CACHE_TTL_SECONDS", "1800")),
        cache_enabled=_truthy("CACHE_ENABLED", "true"),
        host=os.environ.get("HOST", "0.0.0.0"),
        port=int(os.environ.get("PORT", "8000")),
    )

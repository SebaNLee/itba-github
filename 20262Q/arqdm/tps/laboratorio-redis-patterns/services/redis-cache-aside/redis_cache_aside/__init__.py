"""Cache-aside demo service (Valkey + PostgreSQL + Strawberry GraphQL)."""

from redis_cache_aside.asgi import build_app

__version__ = "0.1.0"

__all__ = ["__version__", "build_app"]

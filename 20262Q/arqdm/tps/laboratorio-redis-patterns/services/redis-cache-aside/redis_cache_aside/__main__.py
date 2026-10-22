from __future__ import annotations

import uvicorn

from redis_cache_aside.asgi import build_app
from redis_cache_aside.settings import load_settings


def main() -> None:
    settings = load_settings()
    app = build_app(settings)
    uvicorn.run(app, host=settings.host, port=settings.port)


if __name__ == "__main__":
    main()

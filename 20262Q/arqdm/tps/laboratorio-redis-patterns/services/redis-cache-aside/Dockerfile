# syntax=docker/dockerfile:1
# runtime: ASGI app for the cache-aside lab.
FROM python:3.12-slim AS runtime
WORKDIR /app
ENV PYTHONUNBUFFERED=1
ENV PYTHONPATH=/app
COPY pyproject.toml .
COPY redis_cache_aside ./redis_cache_aside
RUN pip install --no-cache-dir .

# test: pytest with dev extras; Compose service `test`.
FROM runtime AS test
RUN pip install --no-cache-dir ".[dev]"
COPY tests ./tests
CMD ["pytest", "-q"]

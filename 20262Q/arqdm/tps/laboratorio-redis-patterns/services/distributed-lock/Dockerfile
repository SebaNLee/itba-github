# syntax=docker/dockerfile:1
FROM python:3.12-slim AS runtime
WORKDIR /app
ENV PYTHONUNBUFFERED=1
ENV PYTHONPATH=/app
COPY pyproject.toml .
COPY distributed_lock ./distributed_lock
RUN pip install --no-cache-dir .

FROM runtime AS test
RUN pip install --no-cache-dir ".[dev]"
COPY tests ./tests
CMD ["pytest", "-q"]

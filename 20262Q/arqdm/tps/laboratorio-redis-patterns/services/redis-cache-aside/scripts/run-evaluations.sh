#!/usr/bin/env bash
# Run all non-pytest evaluations (specs/02-cli-eval.md, EVALUATE.md).
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${ROOT}"

echo "==> Starting Valkey + PostgreSQL"
docker compose up -d valkey postgres

echo ""
echo "==> Visual evaluation (GraphQL heavy consumer, metrics table)"
docker compose run --rm eval

echo ""
echo "==> Demo script (spec §5, CatalogService in-process)"
docker compose run --rm eval python -m redis_cache_aside.demo

echo ""
echo "Done. See EVALUATE.md for how to interpret output."

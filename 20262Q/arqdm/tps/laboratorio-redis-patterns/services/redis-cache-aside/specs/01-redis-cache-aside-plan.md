# Implementation plan: Cache-aside demo (from `01-redis-cache-aside.md`)

## Iteration 1 — Core domain, cache-aside, metrics

- **Goal:** `CatalogService` with key `catalog:product:{id}`, TTL from `CACHE_TTL_SECONDS`, negative sentinel `{"_absent":true}`, Valkey fallback, `CACHE_ENABLED` bypass, metrics.
- **Tests:** Unit tests with fake cache (mechanism B for TTL); optional mock Redis errors.
- **Validation:** Keys and TTL match spec §3.2–3.3; §3.5–3.6 behavior.
- **Docs:** Constants documented in code + README sentinel section.
- **Spec alignment:** [x] Re-read §3, §4. **Status:** done.

## Iteration 2 — PostgreSQL + Compose + GraphQL API

- **Goal:** `asyncpg` loader, `init.sql` seed, Compose (Valkey, Postgres, app, builder, test with healthchecks), Strawberry schema + `FieldExtension` for `product(id)`, ASGI app, `/metrics`.
- **Tests:** Integration (Given/When/Then/Clean) with real Valkey + Postgres in Compose: cold miss, warm hit, negative cache, TTL mechanism A, GraphQL contract §4.1, Valkey unavailable.
- **Validation:** `docker compose run --rm test` passes; minimum SDL fields.
- **Docs:** README prerequisites, env vars, compose, curl example.
- **Spec alignment:** [x] Re-read §3.4, §4, §6, §7. **Status:** done.

## Iteration 3 — Demo script + final verification

- **Goal:** `redis-cache-aside-demo` compares with/without cache in-process; documents ~80% goal.
- **Tests:** Smoke or unit hook if needed.
- **Validation:** Run demo locally with compose; full pytest green.
- **Docs:** README demo + test commands.
- **Spec alignment:** [x] Re-read §5, §7. **Status:** done.

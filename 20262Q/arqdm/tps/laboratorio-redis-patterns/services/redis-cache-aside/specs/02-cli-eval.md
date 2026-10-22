# Specification: Visual evaluation client (cache-aside lab)

**Version:** 1.0  
**Depends on:** [specs/01-redis-cache-aside.md](01-redis-cache-aside.md)  
**Scope:** A **terminal (“visual”) client** that demonstrates, with **printed metrics**, how a **read-heavy consumer** behaves **with cache-aside enabled** vs **with caching disabled**, aligned with parent spec **§5** (demo script and metrics).  
**Out of scope:** Pytest or other automated test frameworks; production load testing; statistical benchmarking across machines.

---

## 1. Purpose

Learners should **see** (in the terminal) that repeatedly fetching the same **`product(id)`** via the **full GraphQL stack** (query → Strawberry extension → `CatalogService`) generates:

- **Many PostgreSQL reads** when **`CACHE_ENABLED=false`** (cache bypassed at the service layer per parent spec).
- **One cold miss + mostly cache hits** when **`CACHE_ENABLED=true`** and Valkey is warmed for that key, so **DB read count stays near one** for a single hot key under sequential access.

This complements **§6** (pytest): same product semantics and metrics counters, different delivery (Rich UI + narrative, container-run).

---

## 2. Normative requirements

### 2.1 Execution environment

| Rule | Description |
|------|-------------|
| **Container** | The evaluation **MUST** run as a **Docker Compose one-shot** service (e.g. `eval`) using the same **runtime image** as the API (`Dockerfile` `runtime` target), so **`DATABASE_URL`** and **`REDIS_URL`** use Compose service hostnames (`postgres`, `valkey`). |
| **Not pytest** | The artifact **MUST NOT** be implemented as a pytest module; it is a **CLI** invoked with `python -m ...` or a console script entry point. |
| **Infra** | **MUST** depend on healthy **Valkey** and **PostgreSQL**; **MAY** run without the long-running **`app`** container by embedding the ASGI app in-process (recommended) so metrics and GraphQL stay consistent with production code paths. In-process runs **MUST** exercise the real ASGI **lifespan** (pool + Redis client startup) — e.g. Starlette `TestClient` or an explicit lifespan manager compatible with httpx `ASGITransport`. |

### 2.2 Workload: “heavy consumer”

| Rule | Description |
|------|-------------|
| **Operation** | **MUST** issue the **minimum GraphQL query** from parent spec **§4.1**: `product(id)` selecting **`id`**, **`title`**, **`description`**, **`price`**. |
| **Hot key** | **MUST** default to a seeded product id (e.g. **`demo-1`**) so every request targets the same cache key **`catalog:product:{id}`** (parent **§3.2**). |
| **Volume** | **MUST** support a configurable **iteration count** (reference default **1000** requests per phase) to simulate a burst of catalog reads. |
| **Concurrency (optional)** | **MAY** support **concurrent** requests (bounded semaphore) to illustrate **thundering herd** on cold cache (multiple misses before the first `SET`). Sequential mode **MUST** remain the default for predictable pedagogy. |

### 2.3 Phases (with vs without cache)

The client **MUST** run **two phases** in one process, in order:

1. **Cache off:** Build settings with **`cache_enabled=False`** (same env otherwise as parent **§7**). **Flush** Valkey DB used by the demo (or document cold key) so phase 2 starts clean. Run the full iteration workload; **record** wall time and metrics snapshot.
2. **Cache on:** **`cache_enabled=True`**. **Flush** Valkey again, **reset** in-process metrics. Run the **same** workload; **record** wall time and metrics snapshot.

**MUST** present both phases in a **comparison table** (see **§2.4**).

### 2.4 Metrics (minimum)

Aligned with parent spec **§5.2**, the output **MUST** include for **each phase**:

- **Cache hits** and **cache misses**
- **PostgreSQL read count** (`db_reads`)
- **Wall-clock time** for the whole iteration batch

**SHOULD** include **hit ratio** when hits + misses &gt; 0.

**MAY** show **cache write failures** if the implementation surfaces them in the same snapshot (parent **§3.5**).

### 2.5 Visual presentation

| Rule | Description |
|------|-------------|
| **Library** | **SHOULD** use **Rich** (or equivalent) for a **title panel** and a **tabular** comparison. |
| **Narrative** | **MUST** print short **educational hints** (e.g. expected shape: DB reads ≈ iterations when cache off; ~1 DB read + high hit ratio when cache on for sequential hot key). **MUST** restate that **~80% DB reduction** for skewed traffic is a **scenario goal**, not an SLA (parent **§2.1**, **§5.3**). |

### 2.6 Configuration

**MUST** support environment variables (with CLI overrides **MAY**):

| Variable | Purpose |
|----------|---------|
| `VISUAL_EVAL_REQUESTS` | Iteration count (default documented in `EVALUATE.md`). |
| `VISUAL_EVAL_PRODUCT_ID` | GraphQL `id` argument. |
| `VISUAL_EVAL_CONCURRENT` | Max in-flight GraphQL requests (`1` = sequential). |

---

## 3. Orchestration

| Artifact | Role |
|----------|------|
| **`docker-compose.yaml` service `eval`** | One-shot container with `DATABASE_URL`, `REDIS_URL`, `CACHE_TTL_SECONDS` consistent with the lab; command runs the visual eval module. |
| **`scripts/run-evaluations.sh`** | **MUST** start **Valkey** and **PostgreSQL**, then run **all** bundled evaluations (visual eval + parent **§5** demo script) via `docker compose run --rm`. |
| **`EVALUATE.md`** | **MUST** document commands, env vars, what each run shows, and what learners should **expect** in the metrics table. |

---

## 4. Traceability

| Parent spec | This spec |
|-------------|-----------|
| §5 Demo script & metrics | §2.3–2.4 |
| §4.1 GraphQL contract | §2.2 |
| §3.2 Cache key | §2.2 (hot key) |
| §2.1 / §5.3 Educational framing | §2.5 |
| §7 Documentation | `EVALUATE.md`, Compose `eval` |

---

## 5. References

- Parent: [01-redis-cache-aside.md](01-redis-cache-aside.md)

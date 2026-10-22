# Specification: Cache-aside demo (Valkey + GraphQL)

**Version:** 1.5  
**Depends on:** [specs/00-monorepo-gitlab.md](../../../specs/00-monorepo-gitlab.md), [docs/monorepo-practices.md](../../../docs/monorepo-practices.md)  
**Scope:** Educational demo of the **cache-aside** pattern for a **global product catalog** backed by PostgreSQL, with **Valkey** as the cache and a **Strawberry GraphQL** API where the cache-aside logic lives in **middleware** (or equivalent request-layer hooks).  
**Out of scope:** Production hardening (high availability, authn/z, rate limiting beyond demo needs), full CMS integration, cache invalidation on write (beyond TTL), and performance guarantees in arbitrary load tests.

---

## 1. Scenario and problem

### 1.1 Project

**Global Product Catalog for E-commerce** — a read-heavy catalog where product pages (id, title, description, price, etc.) are served to storefronts and integrations.

### 1.2 The problem

A high-traffic retail site experiences **database thrashing** when a popular influencer links to a single product page: many concurrent readers hit the same rows, and the database becomes the bottleneck even though the data changes infrequently compared to read volume.

This demo explains **how cache-aside reduces read load on PostgreSQL** and how **misses** are handled transparently.

---

## 2. Goals

| Goal | Description |
|------|-------------|
| **Teach cache-aside** | Show read path: cache lookup → on miss, load from PostgreSQL → populate cache with TTL. |
| **Measurable reads** | Provide a way to compare **with cache** vs **without cache** (e.g. repeated GraphQL queries) and surface **simple metrics** (hits, misses, DB queries, latency). |
| **Real cache stack** | Run **Valkey** (Redis-compatible) in **Docker Compose** so local and CI runs exercise a real server, not mocks only. |
| **GraphQL surface** | Expose the catalog via **Strawberry** GraphQL; implement cache-aside in **middleware** (or Strawberry extensions/context) so resolvers stay thin and the pattern is visible in one place. |
| **TTL for freshness** | **30 minutes** TTL on cached product payloads so CMS updates eventually appear without requiring this demo to implement push invalidation. |
| **Automated checks** | **Functional tests** prove the service behaves as expected (miss → DB, hit → no extra DB read, expiry/TTL behavior as defined). |
| **Documentation** | **README** (or equivalent) explains how to run Valkey, app, migrations/seeds, and the demo script. |

### 2.1 Success criteria (educational)

- **Target narrative:** Under repeated identical reads, the implementation should demonstrate that **most requests are served from Valkey**, with PostgreSQL consulted primarily on **cache miss** (first access or after TTL). Document that the **80% reduction in database read load** is a **design goal** for realistic traffic skew; the bundled **demo script** should print numbers that illustrate the effect (e.g. hit ratio, counted DB reads), not a formal SLA.
- **Miss handling:** When the key is absent or expired, the resolver path **loads from PostgreSQL**. If a row exists, it **stores** the result in Valkey with **TTL = 30 minutes** (1800 seconds) and returns the data. If no row exists, see **Section 3.6** (**negative caching**).
- **Not production:** Single-node Valkey, simple metrics, no clustering or persistence requirements beyond what Compose needs for a dev laptop.

---

## 3. Architecture (normative)

### 3.1 Components

1. **Valkey** — Cache server; Redis protocol compatible. Keys used for the product cache-aside path **MUST** conform to **Section 3.2**.
2. **PostgreSQL** — System of record for products (minimal schema: at least id + fields needed for the GraphQL `Product` type).
3. **Application** — Python process running **Strawberry** + **ASGI** server (e.g. uvicorn). Dependencies SHOULD include `strawberry-graphql`, `asyncpg` or `psycopg` (or SQLAlchemy), and a Redis-compatible client (`redis`-py works against Valkey).
4. **Cache-aside placement** — **Middleware** (ASGI) or **Strawberry extension** that wraps the GraphQL execution context. For each **`product(id)`** resolution, the cache lookup and store **MUST** use the key defined in **Section 3.2** (derived from that `id`). The flow is:
   - **GET** `catalog:product:{id}` from Valkey.
   - On **miss**, call a **loader** that reads from PostgreSQL for that `id`. If a row exists, **SET** the serialized **Product** with **EX** / TTL per **Section 3.3**. If no row exists, **SHOULD** **SET** a **negative cache** entry per **Section 3.6** (same key, same TTL).
   - On **hit**, deserialize: if the value is the **absent sentinel**, return **`null`**; otherwise return the **Product** **without** querying PostgreSQL for that `id`.

The spec allows either ASGI middleware or Strawberry extensions as long as **one documented layer** owns the cache-aside logic for the demo queries.

### 3.2 Cache key format (normative)

For **single-product** cache-aside (the `product(id: ID!)` read path), implementations **MUST** use exactly this pattern:

```text
catalog:product:{id}
```

Where:

| Part | Rule |
|------|------|
| **`catalog`** | Literal namespace segment; **MUST** be the string `catalog` for this demo. |
| **`product`** | Literal entity segment; **MUST** be the string `product` for product payloads. |
| **`{id}`** | **MUST** be the **canonical product identifier** as it appears in the GraphQL argument and in loaders: a single non-empty string with **no** surrounding whitespace. If the primary key in PostgreSQL is not textual, `{id}` **MUST** be the same string form the API uses (e.g. decimal digits for bigint, UUID string for UUID), consistently everywhere. |

**MUST NOT:** Use a different key shape for the same `product(id)` cache-aside path (e.g. `cache-aside:product:{id}` or `product:{id}` alone) in this service’s reference implementation.

**MAY (future):** Additional catalog entities **SHOULD** follow `catalog:{entity}:{id}` if this spec is extended; until then, only `catalog:product:{id}` is normative for the demo.

### 3.3 TTL

- **TTL:** **1800 seconds (30 minutes)** for keys **`catalog:product:{id}`** (adjust only if documented; default remains 30 minutes).

### 3.4 Docker Compose

- **MUST** include services for at least: **Valkey**, **PostgreSQL**, **app** (or documented two-step: infra + local uvicorn).
- Compose **MUST** be sufficient for **functional tests** that connect to Valkey and PostgreSQL (same images/ports as documented in README).
- **COULD:** Pin the **Valkey** image to a **specific version tag or digest** in `docker-compose.yaml` (avoid an unpinned `:latest` as the only reference) and record the chosen tag or digest in the README so upstream image updates do not surprise CI or local demos.

### 3.5 Valkey unavailability (SHOULD)

Implementations **SHOULD** define and document behavior when Valkey is **unreachable** or **returns errors** on cache operations, so **tests**, **CI**, and **operators** share one expectation.

**Recommended behavior for this demo:** **Fallback to PostgreSQL** — serve catalog reads from the database when the cache cannot be used; **do not** fail the request **only** because Valkey failed (i.e. avoid **fail-fast** on cache outage for `product(id)`).

| Situation | SHOULD behavior |
|-----------|-----------------|
| **`GET`** fails (connection refused, DNS failure, timeout, protocol error) | Treat as **cache miss**: load from PostgreSQL, deserialize, return the **Product**. **SHOULD NOT** turn a reachable DB into a GraphQL error solely due to Valkey. |
| **`SET`** fails after a successful DB load | **SHOULD** still return the row loaded from PostgreSQL; cache population is **best-effort**. MAY log or count **cache write failures** for observability. |
| **Process / ASGI startup** | **SHOULD** either start the app with Valkey optional (degrade at request time) **or** document a deliberate fail-fast startup; the **default story for this lab** is **runtime fallback to DB**, not blocking all reads when Valkey is down. |

**Contrast:** **Fail fast** (e.g. 503 for every product read when Valkey is absent) is **not** the default for this spec; if an implementation chooses it, that **MUST** be stated in README so CI and demos do not assume DB fallback.

**Tests (SHOULD):** At least one automated or documented check that when Valkey is unavailable or stubbed to error, **`product(id)`** still succeeds with data from PostgreSQL when the row exists (see Section 6.2).

### 3.6 Absent product (not in DB) and negative caching (SHOULD)

Implementations **SHOULD** specify how **`product(id)`** behaves when **no row** exists for `{id}` in PostgreSQL, and **SHOULD** use **negative caching** so repeated lookups for missing ids do not **thrash** the database.

| Topic | SHOULD rule |
|-------|----------------|
| **GraphQL result** | **`product(id)`** **SHOULD** return **`null`** (nullable **`Product`**) when there is no product for that `id`. |
| **Negative cache** | After the loader confirms **no row**, **SHOULD** **SET** **`catalog:product:{id}`** with a **documented absent sentinel** value and **EX** equal to **Section 3.3** (**same TTL** as a hit on a real product — default **1800 s**). |
| **Hit on sentinel** | On **GET** hit, if the stored value decodes to the **absent sentinel**, **SHOULD** return **`null`** **without** querying PostgreSQL. |
| **Sentinel format** | **MUST** be **documented** in README and in code (constant or schema). **MUST** be distinguishable from any valid serialized **Product** (e.g. a reserved JSON object like `{"_absent": true}` or another agreed literal). **MUST** use the **same key** as **Section 3.2** — do **not** use a separate Redis key only for “unknown”. |

**Contrast:** **No negative caching** (leave key unset after DB miss) is **not** the default for this lab; it forces a **DB read on every** request for a chronically missing `id`.

**Note:** When Valkey is unavailable (**Section 3.5**), fallback still consults PostgreSQL; negative entries are not stored if **SET** fails.

---

## 4. GraphQL API

### 4.1 Minimum schema contract (SHOULD)

Implementations **SHOULD** expose **at least** the following **GraphQL surface** unchanged in **name and shape** so **tests**, the **demo script**, and **examples** stay aligned. This is the **stable contract** for this service; implementations **MAY** add queries, fields, or types, but **SHOULD NOT** remove, rename, or change the type of the following operations and fields without bumping this spec.

**Query**

| Name | Signature (conceptual SDL) | Notes |
|------|----------------------------|--------|
| **`product`** | `product(id: ID!): Product` | Return type is **nullable**: resolver returns **`null`** when no product exists (**Section 3.6**). Argument name **`id`** is fixed. |

**Type `Product`**

| Field | GraphQL type | Notes |
|-------|----------------|--------|
| **`id`** | **`ID!`** | Canonical product identifier (consistent with **`{id}`** in **Section 3.2**). |
| **`title`** | **`String!`** | Display title. |
| **`description`** | **`String!`** | Product description text. |
| **`price`** | **`Float!`** | Unit price (demo uses scalar **Float**; not a production money type). |

**SDL sketch (non-authoritative; equivalent shapes satisfy the contract):**

```graphql
type Query {
  product(id: ID!): Product
}

type Product {
  id: ID!
  title: String!
  description: String!
  price: Float!
}
```

**Rationale:** Locking these names lets automated tests and the demo script issue **fixed** operations without chasing refactors; optional pagination (e.g. `products`) remains **MAY** outside this minimum.

### 4.2 Implementation notes

- Resolvers SHOULD NOT duplicate cache-aside logic if middleware/extension already guarantees cached reads under **`catalog:product:{id}`** (Section 3.2).
- Implementation MAY use DataLoader for batching, but the **educational focus** is cache-aside for read optimization, not GraphQL N+1 theory.

---

## 5. Demo script and metrics (normative)

### 5.1 Purpose

Provide a **script** (shell or Python CLI, checked into the service) that:

1. Runs a **warm-up** or clears cache (optional step) to show **cold** behavior.
2. Executes GraphQL operations **without** using the cache (e.g. env flag `CACHE_ENABLED=false` or bypass route) for a fixed number of iterations and prints **metrics** (elapsed time, DB query count if instrumented). The operations **SHOULD** use the **minimum schema** in **Section 4.1** (`product(id)` selecting **`id`**, **`title`**, **`description`**, **`price`**) so results stay comparable across runs and with tests.
3. Executes the **same** operations **with** cache enabled and prints **metrics** again (hits, misses, latency, DB reads).

### 5.2 Metrics (minimum)

The script or app SHOULD expose or print at least:

- **Cache hits** and **cache misses** (or equivalent: Valkey `GET` success vs miss).
- **PostgreSQL read count** for the product path (or a single counter incremented only in the loader).
- **Wall-clock time** or average latency for the batch.

Optional: simple **hit ratio** = hits / (hits + misses).

### 5.3 Educational framing

The script MUST document that **80% DB read reduction** is the **scenario goal**; the printed numbers validate the **mechanism**, not a production benchmark.

### 5.4 Latency narrative (COULD)

Documentation and/or the demo script **MAY** include an **informal** latency story to reinforce cache benefit — for example, that **warm** **`product(id)`** requests (cache hit, localhost, minimal load) often complete in **on the order of tens of milliseconds or less** (e.g. **under ~10 ms** server-side excluding client network), while **cold** or **uncached** paths are typically slower due to PostgreSQL.

This is **not** a formal SLA, **not** required in CI, and **not** machine-independent; it exists only to give learners a **qualitative** expectation alongside hit counts and DB read metrics.

---

## 6. Functional validation (tests)

Automated tests **MUST** verify behavior (pytest; plain functions; Given/When/Then/Clean where applicable).

### 6.1 TTL expiry test mechanism (normative)

The **TTL expiry** row in Section 6.2 **MUST** be implementable without relying on the spec reader to guess timing strategy. Each TTL expiry test **MUST** follow **one** of the following mechanisms end-to-end (document **A** vs **B** in the test’s docstring or README):

| Mechanism | Normative rules |
|-----------|-----------------|
| **A. Short TTL + wall-clock wait (integration)** | Override the application TTL used for `catalog:product:{id}` to a **small positive integer** — **MUST** be **1–60 seconds** inclusive (recommended **1–3 s** for CI). Use the same mechanism as production config (e.g. **`CACHE_TTL_SECONDS`** in test env or pytest fixture). After a value is cached, wait until the key has expired using **either**: (1) **`time.sleep(TTL + margin)`** with margin **≥ 0** (small fraction of a second is typical), **or** (2) a **polling loop** against Valkey (**`TTL`**, **`GET`**, or **`EXISTS`**) until the key is absent or expired, with a **maximum wait timeout** so tests cannot hang. Then assert the next read **misses** and the PostgreSQL loader runs. |
| **B. Mock / fake cache or clock (unit)** | Inject a **mock** Redis/Valkey client, a **fake** in-memory cache with controllable expiry, or a **stubbed clock** so that “after expiry” is deterministic without real delays. The test **MUST** assert that after the simulated expiry, the next resolution **misses** and the loader runs. |

**MUST NOT:** Use the production default **1800 s** TTL and a **30-minute** (or similarly long) real-time wait in automated tests.

**SHOULD:** Include at least one test path using **mechanism A** against **real Valkey** (Compose or testcontainers) so expiry is validated against actual server TTL semantics; mechanism **B** **MAY** be used for fast isolated tests.

### 6.2 Required scenarios

| Test idea | Expectation |
|-----------|-------------|
| **Cold miss** | First read for `catalog:product:{id}` for an **existing** product loads from PostgreSQL and populates Valkey. |
| **Warm hit** | Second read for the same `id` does **not** increment DB loader (or equivalent observable); Valkey key unchanged per Section 3.2. |
| **Unknown id + negative cache (SHOULD)** | Per **Section 3.6**: first query for an `id` with **no** DB row returns **`null`** and **SHOULD** store the absent sentinel under **`catalog:product:{id}`**. A second query for the same `id` **SHOULD NOT** increment the DB loader while the sentinel is unexpired; response **`null`**. |
| **TTL expiry** | Per **Section 6.1**: after the key for `catalog:product:{id}` has expired (short TTL + wait/poll, or mock), the next read is a **miss** and reloads from PostgreSQL. |
| **Integration** | With Valkey (and Postgres) up via Compose or testcontainers, GraphQL query returns consistent data end-to-end per **Section 4.1** (minimum fields). |
| **Valkey unavailable (SHOULD)** | Per **Section 3.5**: when Valkey cannot be used, **`product(id)`** **SHOULD** still return data from PostgreSQL for existing rows. Tests **SHOULD** assert this where feasible (e.g. invalid `REDIS_URL`, stopped cache container with app pointed at dead host, or injected failing Redis client). |

---

## 7. Documentation (normative)

The service **MUST** include user-facing documentation (typically `README.md` in the service directory) covering:

1. **Prerequisites** — Docker, Compose, Python version.
2. **Start stack** — `docker compose up` (or equivalent) and environment variables (`DATABASE_URL`, `REDIS_URL` / Valkey URL, `CACHE_TTL_SECONDS`, optional `CACHE_ENABLED`).
3. **Seed data** — How to load sample products into PostgreSQL.
4. **Run API** — Host/port and example GraphQL query (curl or HTTP file) using the **minimum schema** in **Section 4.1** so examples match tests and the demo script.
5. **Run demo script** — Command and how to interpret **with vs without** cache and metrics.
6. **Run tests** — Local and CI (`docker compose run test` per monorepo practices).
7. **Degraded cache / Valkey down** — State that the service **SHOULD** **fall back to PostgreSQL** per **Section 3.5** (not fail-fast on cache alone), and how that affects demos and CI expectations.
8. **Unknown product ids** — Document **negative caching** per **Section 3.6**: **`null`** response, sentinel format, and same TTL as positive entries.

---

## 8. Implementation notes (non-normative)

- **Valkey vs Redis:** Use the **Valkey** image in Compose for alignment with the lab name; protocol is compatible with standard Redis clients.
- **Serialization:** JSON or msgpack for **Product** and sentinel payloads in Valkey; keep in sync with the **Product** shape implied by **Section 4.1**.
- **Security:** Demo MAY use default passwords on localhost only; document that this is not for production.

---

## 9. Traceability

| Requirement | Where addressed |
|-------------|-----------------|
| Cache key `catalog:product:{id}` | Section 3.2 |
| Reduce DB read load (target ~80% for hot keys) | Demo script metrics + README explanation |
| Miss → PostgreSQL | Cache-aside loader in middleware/extension |
| TTL 30 minutes | `CACHE_TTL_SECONDS=1800` (default); keys per Section 3.2 |
| Valkey for functional testing | `docker-compose.yaml` + tests |
| GraphQL + Strawberry + middleware | Section 3 & 4 |
| Minimum GraphQL fields / `product` contract (SHOULD) | Section 4.1; tests Section 6; demo Section 5 |
| Valkey image pin in Compose (COULD) | Section 3.4 |
| Informal warm-path latency narrative (COULD) | Section 5.4 |
| Functional validation | Section 6, `tests/` |
| TTL expiry test mechanism | Section 6.1 (short TTL + sleep/poll vs mock) |
| Valkey unavailable → DB fallback (SHOULD) | Section 3.5; tests Section 6.2; README Section 7 |
| Absent `product(id)` → `null` + negative cache TTL (SHOULD) | Section 3.6; Section 4; tests Section 6.2; README Section 7 |
| How to use | Section 7, README |

---

## 10. References

- Redis/Valkey cache-aside pattern (conceptual): application manages cache population on read miss.
- Strawberry GraphQL: [https://strawberry.rocks/](https://strawberry.rocks/)
- Valkey: [https://valkey.io/](https://valkey.io/)

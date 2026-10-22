# Specification: Distributed lock — Flash Sale Inventory Guard (Valkey)

**Version:** 1.4  
**Depends on:** [specs/00-monorepo-gitlab.md](../../../specs/00-monorepo-gitlab.md), [docs/monorepo-practices.md](../../../docs/monorepo-practices.md)  
**Scope:** Educational demo of **distributed locking** to prevent **overselling** during a **limited drop**: many concurrent **consumer** processes attempt to **reserve** units from a shared **in-memory inventory** exposed by a small **inventory server**, while a **Redlock** client coordinates **exclusive access** per `product_id` across **three independent Valkey masters** using a **unique lock token** per holder and **quorum** rules. The catalog **defaults to five SKUs**; consumers **choose a product at random** among SKUs **not yet exhausted** each iteration.  
**Out of scope:** Cross-datacenter deployment tuning beyond what the **original Redlock** specification discusses, Valkey **Cluster** / **Sentinel** as alternative HA topologies, persistence tuning, formal correctness proofs, and e-commerce payment flows beyond “decrement stock if lock acquired.”

---

## 1. Scenario and problem

### 1.1 Project

**Flash Sale Inventory Guard** — during a **limited drop**, **many** microservice instances (e.g. **50** replicas) process orders for a **small** fixed stock per **SKU** (e.g. **100** units each, configurable). The demo **defaults to five** distinct **`product_id`** values (`flash-sale-001` … `flash-sale-005`); a **single-SKU** run is supported via environment configuration (see **Section 6.4**).

### 1.2 The problem

Without coordination, **race conditions** let multiple instances read the same remaining count and **decrement** it concurrently, so **more units are sold than exist** (**overselling**). The demo must show how a **distributed lock** (with **token-based release**) ensures **at most one** instance at a time runs the **critical section** that observes and updates inventory for a given **`product_id`**.

---

## 2. Goals

| Goal | Description |
|------|-------------|
| **Teach locking** | Show **acquire → critical section → release** around inventory mutation using **full Redlock** over **three** independent **Valkey** masters (**quorum** = **2**). |
| **Token safety** | Each successful **Redlock acquire** **MUST** use a **new UUID v7** string (RFC 9562) as the **lock value** on every participating node; **per-node release** **MUST** succeed **only** if the client proves it holds **that** token (prevents a **stale** or **zombie** process from deleting another holder’s lock on that node). |
| **In-memory inventory** | The **authoritative stock count** for the demo **MUST** live **in process memory** inside the **inventory server** (not in Valkey). Valkey nodes hold **locks only**, not the stock counter. |
| **Observable story** | **Inventory server** and **consumer** processes **MUST** log when units are **consumed** and when stock is **exhausted**, so traces and Compose logs tell a clear story. |
| **Runnable topology** | **Docker Compose** **MUST** bring up **three** independent **Valkey** services, the **inventory server**, and **one or more consumer** services (or a documented scale pattern) so a single command runs the full **flash sale** simulation until stock hits zero. |
| **Documentation** | **README** **MUST** give a **pedagogic** explanation: why races happen, how the lock key and **Redlock** (**N = 3**, **quorum = 2**) work, and the production caveats required by **Section 8** (item 6). |

### 2.1 Success criteria (educational)

- **No oversell:** Let **P** be the number of distinct **`product_id`** values configured for the server (see **Section 6.4**). When **all** SKUs are exhausted, total successful **consumptions** logged across all consumers **MUST** equal **`P × INITIAL_STOCK`**. If the run stops before all SKUs are exhausted, the total **MUST NOT** exceed **`P × INITIAL_STOCK`**. Verified by counting `consumed` log lines across all container outputs (or equivalent events).
- **Concurrency:** With multiple consumers, at least two distinct `consumer_id` values **MUST** appear in `acquired` log lines before the first `exhausted` line — proving real concurrent participation.
- **Wrong-token release:** A unit test **MUST** verify on at least one Valkey instance: (1) client A acquires the lock and receives `token_A`; (2) client B attempts release with `token_B ≠ token_A`; (3) after the release attempt, the lock key still exists **and** its value still equals `token_A`. See **Section 7** for the full test definition.
- **Completion time:** With a **single** configured `product_id` (e.g. only `flash-sale-001`), `INITIAL_STOCK=100`, and `--scale consumer=5`, the full run **SHOULD** complete within **60 seconds** on a standard laptop (classroom sanity check, not a hard SLA). The default **five-SKU** catalog includes a **random 0–3 s delay** after each successful purchase (**Section 5.1**), so wall-clock time is **not** bounded by that 60 s figure.

---

## 3. Architecture (normative)

### 3.1 Roles

| Component | Responsibility |
|-----------|----------------|
| **Valkey (×3)** | **Three independent masters** (**not** Valkey Cluster for the lock algorithm): each node stores locks only; together they implement **Redlock** via **quorum** (see **Sections 3.3–3.4**). **MUST NOT** store the authoritative `inventory_count` for this lab. |
| **Inventory server** | HTTP **API** (see **Section 4.3**) that keeps **`inventory_count` in memory** for one or more **`product_id`** values (default: five SKUs). Exposes a **consume** endpoint that **MUST** be called only while the caller holds the **distributed lock** for that `product_id` (enforced by convention in consumers; the server **MAY** reject or log if called without a `X-Lock-Token` header — optional hardening). |
| **Consumer service(s)** | One or more processes that **loop**: pick a **non-exhausted** `product_id` **at random** → **acquire lock** for that id → **call inventory server to consume one unit if stock > 0** → **release lock** → repeat until **every** configured SKU reports **out of stock** (see **Section 5.1**), or another termination condition applies. |

Implementations **MUST** define the **inventory server** and **consumer** (or consumers) as **separate Compose services** that use the **same image** with **different `command`s** (e.g. different CLI entry points such as `inventory-server` vs `inventory-consumer` in one Python package).

### 3.2 Lock key format (normative)

**Catalog resolution** (**MUST** be identical on inventory server and consumers):

1. If **`PRODUCT_IDS`** is set (comma-separated, non-empty), **MUST** use that ordered list of SKUs (trim whitespace around each id).  
2. Else if **`PRODUCT_ID`** is set (non-empty), **MUST** use a **single-SKU** catalog containing only that value.  
3. Else **MUST** default to **five** ids: **`flash-sale-001`** through **`flash-sale-005`**.

No surrounding whitespace is permitted in individual ids. The lock key below applies **per** resolved `product_id`.

For a given **`product_id`** (non-empty string, stable across the demo):

```text
lock:inventory:{product_id}
```

| Part | Rule |
|------|------|
| **`lock`** | Literal namespace; **MUST** be `lock` for this service. |
| **`inventory`** | Literal segment; **MUST** be `inventory`. |
| **`{product_id}`** | **MUST** match the catalog identifier used by the inventory server (no surrounding whitespace). |

### 3.3 Per-node lock primitives (normative)

These rules apply to **each** Valkey **master** independently. The **Redlock** client **MUST** use them on **every** node participating in a lock (see **Section 3.4**).

**Acquire (single node)**

- Client generates a **new** **UUID v7** string **`token`** before each **Redlock** acquire attempt (same **`token`** for all nodes in that attempt).
- **MUST** use an atomic **“set if absent”** with **TTL** on `lock:inventory:{product_id}` (e.g. Valkey **`SET key token NX PX <ttl_ms>`**).  
- On this node: if the **SET** succeeds, this node records **`token`** for that resource; if the **SET** fails because the key exists, another client holds the lock **on this node**.

**Release (single node)**

- **MUST NOT** simply **`DEL`** the key without checking value — that would allow **any** process to steal the lock from a legitimate holder after expiry.  
- **MUST** use a **single atomic** operation that deletes **only if** the stored value **equals** this client’s **`token`** (e.g. **Lua** `EVAL` with `GET` + compare + `DEL`, or equivalent **Redis 6+** feature if used and documented).  
- If the stored value **does not** match **`token`**, release on this node **MUST** be a **no-op** (lock remains or was already taken over) and **SHOULD** log at **warning** level for teaching.

**TTL**

- Lock TTL **MUST** be configurable via the `LOCK_TTL_MS` environment variable. The default **MUST** be **10 000 ms (10 s)**. Document the default in README.

### 3.4 Redlock client (normative, N = 3)

**Topology**

- The deployment **MUST** include **exactly three** independent Valkey **masters** — Compose service names **`valkey-1`**, **`valkey-2`**, and **`valkey-3`** — each with its own data set (**no** replication or cluster mode **for the lock algorithm**; they are unrelated single instances used as separate Redlock participants).

**Quorum**

- With **N = 3**, **quorum** **MUST** be **⌊N/2⌋ + 1** = **2**: a lock is **considered acquired** only if the client succeeds on **at least two** masters for the same **`token`** and key.

**Acquire (Redlock)**

- **MUST** follow the **original Redlock** specification: measure elapsed time, attempt **`SET … NX PX`** on **all three** nodes with the **same** **`token`** and **TTL**, count successes, and compute the lock **validity time** (TTL minus elapsed and **clock drift** terms) exactly as defined there.  
- If **fewer than quorum** nodes accept the acquire, the client **MUST** run the **per-node release** (Lua) on **every** node where **`SET`** succeeded before retrying (no partial locks left behind).  
- If **quorum** is reached, the client **holds** the Redlock and **MUST** use the **same** **`token`** for **release** on all nodes after the critical section. The consumer **SHOULD** treat the lock as invalid if **validity ≤ 0** before entering the critical section and **MUST NOT** call **`/consume`** in that case: it **MUST** release the lock (best-effort), log at **warning** level, and retry the acquire from the start of the iteration (counted against `MAX_ITERATIONS`).

**Failed acquire / retry behaviour**

- When **fewer than quorum** nodes accept the acquire (after releasing partial locks), the consumer **MUST** wait a short random back-off (default: uniform random in **[50 ms, 200 ms]**) before retrying. This back-off **MUST** be configurable via `ACQUIRE_RETRY_DELAY_MS_MIN` and `ACQUIRE_RETRY_DELAY_MS_MAX` environment variables with the above defaults. The failed attempt **MUST** count against `MAX_ITERATIONS` to bound CI run time.
- If the inventory server is **unreachable** while the consumer holds a valid lock, the consumer **MUST** release the lock (best-effort), log the error at **error** level, and retry the iteration (counted against `MAX_ITERATIONS`). It **MUST NOT** exit solely because of a request error unless `MAX_ITERATIONS` is reached, **repeated acquire failures** exhaust the configured limit (**below**), or all SKUs are exhausted.

**Repeated failed acquires**

- If **Redlock acquire** fails (**fewer than quorum** after partial release), the consumer **MUST** increment a **consecutive failure** counter. If that counter reaches **`MAX_CONSECUTIVE_ACQUIRE_FAILURES`** (default **5**), the consumer **MUST** exit cleanly with a **distinct non-zero exit code** (e.g. **3**) and log a **warning**. A **successful** acquire **MUST** reset the counter to zero. This is independent of `MAX_ITERATIONS`.

**Release (Redlock)**

- **MUST** execute the **per-node** release script on **all three** masters (best-effort on each), using the **`token`** from the successful acquire.

**Consumer loop**

- If the inventory server returns **out-of-stock** (HTTP **409** — see **Section 4.3**) for a given **`product_id`**, the consumer **MUST** record that SKU as **exhausted** for this process and **continue** the loop (other SKUs may still have stock). When **no** non-exhausted SKU remains in the configured catalog, the consumer **MUST** exit its loop **without** infinite spinning. This **MUST NOT** be confused with **lock acquire failure** (**Repeated failed acquires** above).

**TTL expiry during the critical section**

- If the lock **validity** or per-node TTL expires **while** the consumer is executing the critical section (e.g. HTTP call to the inventory server still in flight), the consumer **MUST** proceed with **best-effort** **Redlock release** after the call returns. Per-node Lua no-ops when the token no longer match are safe; the consumer **SHOULD** log at **warning** when a release returns no-match on any node.  
- This scenario is intentionally left as an observable race for educational purposes. Consumers **MUST NOT** abort the inventory call mid-flight solely because TTL or validity may have expired.

### 3.5 Relation to production deployments

- This lab implements **full Redlock** with **N = 3** in Compose. **Production** multi-datacenter and **clock drift** caveats remain as in the **original Redlock** specification; the **README** **MUST** summarize them per **Section 8** (item 6).

---

## 4. Inventory server (normative)

### 4.1 In-memory state

- **MUST** hold a map **`product_id` → remaining count** for every id returned by the same **catalog resolution** as **Section 3.2** (default: **five** SKUs).  
- **Initial stock** per SKU (e.g. **100**) **MUST** be configurable via **`INITIAL_STOCK`** with a documented default; the **same** initial value **MUST** apply to **each** configured `product_id`.  
- **MUST** expose an operation that **decrements by one** if count **> 0** and returns **success/failure** (and remaining count or “exhausted”).  
- Counters **MUST NOT** be persisted to Valkey or disk for this spec (restart resets stock — acceptable for the lab).
- The inventory server **MUST** run as a **single-worker async HTTP server** (e.g. **FastAPI** + **Uvicorn** with `--workers 1`). The single-threaded async event loop ensures the in-memory counter is accessed by at most one coroutine at a time, eliminating the need for explicit locking inside the server. Multi-worker deployments are **out of scope** for this lab.

### 4.2 HTTP API (normative)

The server **MUST** expose the following endpoints. All request and response bodies are **JSON** (`Content-Type: application/json`).

#### `POST /consume`

Attempt to decrement stock by one for a given `product_id`.

**Request body:**
```json
{ "product_id": "flash-sale-001" }
```

**Responses:**

| HTTP status | Meaning | Response body |
|-------------|---------|---------------|
| `200 OK` | One unit consumed. | `{ "product_id": "...", "remaining": <int> }` |
| `409 Conflict` | Stock exhausted; no units available. | `{ "product_id": "...", "remaining": 0, "exhausted": true }` |
| `404 Not Found` | `product_id` is syntactically valid but not recognised by this server instance. | `{ "error": "<message>" }` |
| `422 Unprocessable Entity` | `product_id` field is missing or structurally invalid (empty string, wrong type). | `{ "error": "<message>" }` |

The optional `X-Lock-Token` request header MAY carry the consumer's lock token for server-side logging or hardening (see **Section 3.3**). The server **MUST NOT** enforce lock presence unless the optional hardening is explicitly enabled.

#### `GET /health`

Returns `200 OK` with `{ "status": "ok" }`. Used by Compose `healthcheck`.

### 4.3 Logging (normative)

The **inventory server** **MUST** log (structured or plain text, but consistent):

- Each **successful** decrement: include **`product_id`**, **remaining after**, and the value of `X-Lock-Token` header if present.  
- When stock reaches **0**: a clear **exhausted** message including **`product_id`** and total **units sold** (should match initial stock).

---

## 5. Consumer services (normative)

### 5.1 Behavior

- **MUST** resolve the **same** product catalog as the server (**Section 3.2**). Each iteration **MUST** choose **`product_id`** **uniformly at random** from the set of SKUs **not yet marked exhausted** by this consumer (after a **409** response for that id, it **MUST** be excluded from subsequent random choices).
- **MUST** run until **all** configured SKUs are exhausted **from this consumer’s perspective** (no candidates left), **or** `MAX_ITERATIONS` iterations complete, **or** **consecutive** failed **Redlock acquires** reach **`MAX_CONSECUTIVE_ACQUIRE_FAILURES`** (**Section 3.4**), whichever comes first.
  - `MAX_ITERATIONS` **MUST** be configurable via environment variable; default **MUST** be **`1000`** (hard ceiling for CI runs).
  - When `MAX_ITERATIONS` is reached before every SKU is exhausted, the consumer **MUST** log a **warning** (e.g. `"MAX_ITERATIONS reached; exiting without exhausting stock"`) and exit cleanly with a non-zero exit code (e.g. **2**) so CI can distinguish a ceiling hit from normal completion.
  - When **`MAX_CONSECUTIVE_ACQUIRE_FAILURES`** is reached, the consumer **MUST** log a **warning** and exit with a **different** non-zero exit code (e.g. **3**) (**Section 3.4**).
- Each iteration: **Redlock acquire** for the chosen `product_id` → **POST /consume** → **Redlock release** in a `finally` block (so release always runs even if the HTTP call fails).
- **MUST** use the **same** **`token`** for **release** on all nodes as for the successful **Redlock acquire** for that iteration.
- On receiving **`409`** for a given `product_id`, the consumer **MUST** mark that SKU exhausted and **continue** (see **Section 3.4** — **Consumer loop**). **MUST NOT** treat a single-SKU `409` as global shutdown when other SKUs remain.
- After a **successful** consume (**HTTP 200**), the consumer **MUST** sleep a **random** duration **uniformly distributed in [0 s, 3 s]** before starting the next iteration (simulates pacing between purchases).
- Failed acquires (before quorum) and validity-expired retries **MUST** each count as one iteration against `MAX_ITERATIONS` (see **Section 3.4** — **Failed acquire / retry behaviour**).

### 5.2 Logging (normative)

Each **consumer** **MUST** include its `CONSUMER_ID` (see **Section 6.2**) in every log line. Required log events:

- **Acquired** lock: include `product_id` and `consumer_id`.  
- **Consumed** one unit: include `product_id`, `consumer_id`, and `remaining` if returned by API.  
- **Exhausted** / **out of stock** for a SKU when the server returns **`409`** (include `product_id`).  
- **All products exhausted** when the consumer stops because no non-exhausted SKU remains.  
- **Release no-match** at **warning** level when the Lua script returns 0 (token mismatch — indicates TTL expiry race).  
- **Failed acquire** (optional at debug) and **skipped release** when no acquire.

---

## 6. Docker Compose (normative)

### 6.1 Services

`docker-compose.yaml` **MUST** define at least:

| Service | Purpose |
|---------|---------|
| **`valkey-1`**, **`valkey-2`**, **`valkey-3`** | Three **independent** lock servers; image **MUST** use a **pinned minor-version tag** (e.g. `valkey/valkey:7.2`); each exposes port **6379** on the Compose network (distinct service DNS names). **MUST** include a `healthcheck` using `valkey-cli ping` on each. |
| **`inventory-server`** | Builds from this service’s **Dockerfile**; `depends_on` **MUST** wait until **`valkey-1`**, **`valkey-2`**, and **`valkey-3`** are **healthy** (ordering only — the server does not use Valkey); exposes HTTP port **8000** on the Compose network. **MUST** include a `healthcheck` using `GET /health`. |
| **`consumer`** | **Same image** as **`inventory-server`** with a **different `command`**; `depends_on` **MUST** wait until **`inventory-server`** and all three **`valkey-*`** services are **healthy**; **MUST** connect to **all Valkey** endpoints and **inventory-server** via service names. |

Each Compose service **SHOULD** declare resource limits to prevent resource exhaustion on student laptops:

```yaml
deploy:
  resources:
    limits:
      memory: 128m
```

### 6.2 Scale

- **MUST** document how to run multiple consumer instances: `docker compose up --build --scale consumer=5`.  
- Each consumer instance **MUST** receive a distinct `CONSUMER_ID`. When using `--scale`, implementations **SHOULD** fall back to the container hostname (`socket.gethostname()`) when `CONSUMER_ID` is not set, so each scaled replica is identifiable in logs without manual configuration.

### 6.3 Redlock implementation

Implementations **MAY** hand-roll the Redlock acquire/release logic (recommended for educational value — students follow the algorithm step by step) or use an existing library (e.g. `pottery`, `redis-py`'s lock helpers, or a dedicated Redlock package). Either approach **MUST** satisfy all normative requirements in **Sections 3.3–3.4**. If a library is used, the README **MUST** name it and note which algorithmic steps it handles on behalf of the consumer code.

### 6.4 Environment

All services **MUST** receive configuration exclusively via environment variables. Required variables and their defaults:

| Variable | Default | Used by |
|----------|---------|---------|
| `REDLOCK_VALKEY_URLS` | Comma-separated: `redis://valkey-1:6379/0,redis://valkey-2:6379/0,redis://valkey-3:6379/0` | consumer |
| `INVENTORY_URL` | `http://inventory-server:8000` | consumer |
| `PRODUCT_IDS` | `flash-sale-001,flash-sale-002,flash-sale-003,flash-sale-004,flash-sale-005` | server, consumer |
| `PRODUCT_ID` | *(unset)* | server, consumer |
| `INITIAL_STOCK` | `100` | server |
| `LOCK_TTL_MS` | `10000` | consumer |
| `MAX_ITERATIONS` | `1000` | consumer |
| `MAX_CONSECUTIVE_ACQUIRE_FAILURES` | `5` | consumer |
| `CONSUMER_ID` | *(hostname fallback)* | consumer |
| `ACQUIRE_RETRY_DELAY_MS_MIN` | `50` | consumer |
| `ACQUIRE_RETRY_DELAY_MS_MAX` | `200` | consumer |

**Catalog precedence:** If **`PRODUCT_IDS`** is set, it **MUST** override **`PRODUCT_ID`**. If **`PRODUCT_IDS`** is unset and **`PRODUCT_ID`** is set, the catalog **MUST** be a **single** SKU. If **both** are unset, implementations **MUST** use the **five** default ids above (**Section 3.2**). **`PRODUCT_IDS`** and **`PRODUCT_ID`** **MUST** stay consistent between **inventory-server** and **consumer** Compose services.

---

## 7. Automated checks (SHOULD)

### 7.1 Test infrastructure

Tests **MUST** use **real Valkey** containers via **`pytest-docker`** or **`testcontainers-python`** (e.g. `testcontainers[redis]`). `fakeredis` **MUST NOT** be used for tests that exercise the Lua release script, as its Lua compatibility is incomplete and may produce false passes.

- **Per-node** tests (**T-01**–**T-03**) **MAY** use a **single** Valkey instance to validate **`SET NX`** and **Lua** behavior.  
- **Integration** tests that assert **Redlock** behavior (**T-04** and any **no-oversell** smoke) **MUST** run against **three** independent Valkey instances (e.g. three **testcontainers**).

### 7.2 Required test cases

All tests **SHOULD** use **pytest** (monorepo convention). The following cases **MUST** be present:

#### T-01: Mutual exclusion
- **Setup:** start a real Valkey container; acquire the lock for `flash-sale-001` with `token_A`.
- **Action:** attempt a second acquire for the same key with `token_B` (no back-off, single attempt).
- **Assert:** the second acquire returns failure (key exists); `GET lock:inventory:flash-sale-001` returns `token_A`.

#### T-02: Wrong-token release is a no-op
- **Setup:** acquire lock with `token_A`; generate a different `token_B`.
- **Action:** execute the release script with `token_B`.
- **Assert:** the Lua script returns `0` (no delete); `GET lock:inventory:flash-sale-001` still returns `token_A`.

#### T-03: Correct-token release succeeds
- **Setup:** acquire lock with `token_A`.
- **Action:** execute the release script with `token_A`.
- **Assert:** the Lua script returns `1`; `EXISTS lock:inventory:flash-sale-001` returns `0`.

#### T-04: Smoke — no-oversell (scripted scenario)
- **Setup:** start **three** Valkey instances + inventory server (testcontainers or subprocess); configure the consumer with **`REDLOCK_VALKEY_URLS`** listing all three; set `INITIAL_STOCK=10`; set **`PRODUCT_ID=flash-sale-001`** (or **`PRODUCT_IDS`** to a **single** id) so the test exercises **one** SKU and total sold remains **10**.
- **Action:** run **3** consumer threads (each with a distinct `CONSUMER_ID`) concurrently until all exit, using the **Redlock** acquire/release path.
- **Assert:**
  - Total `consumed` events equals **`10`** (not merely ≤ 10) and final inventory server state for that `product_id` reports `remaining=0`.
  - At least **2** distinct `CONSUMER_ID` values appear in `consumed` events — proving real concurrent participation (satisfies the **Concurrency** criterion in **§2.1**).

---

## 8. README (normative)

The service **`README.md`** **MUST** include:

1. **Problem:** race when many workers decrement shared stock.  
2. **Solution sketch:** one logical lock per `product_id` via **Redlock** (**N = 3**, **quorum = 2**); **UUID v7** as **proof of ownership**; **TTL** / **validity** for crash safety.  
3. **Why not `DEL` alone:** lost vs stolen lock; **compare-and-delete** with **token**.  
4. **How to run:** `docker compose up --build` (and scale example).  
5. **Where to look in logs:** **exhausted** on server vs consumers.  
6. **Redlock in this lab:** A short subsection **MUST** cover all of the following:  
   - **This repository** implements **full Redlock** with **N = 3** independent Valkey **masters** and **quorum = 2**, per the **original Redlock** specification (including **validity time** and **clock drift** terms).  
   - How **acquire** (quorum) and **release** (all nodes) map to the **per-node** **`SET NX`** and **Lua** release.  
   - **Production** caveats: multi-datacenter latency, **clock drift**, and why **three** single-node masters in Compose **do not** replace a production HA story (Cluster/Sentinel, etc.) — brief **pedagogic** paragraph.

---

## 9. Traceability

| Requirement | Section |
|-------------|---------|
| One writer at a time per `product_id` | **3.3**, **3.4**, **5.1** |
| UUID v7 / token release | **3.3**, **3.4**, **2** |
| In-memory stock (multi-SKU map) | **3.1**, **4.1** |
| Single-worker async server (thread safety) | **4.1** |
| Valkey only for locking | **3.1** |
| Redlock **N = 3**, **quorum = 2** | **3.4**, **6**, **6.4** |
| HTTP API contract (endpoints + schemas) | **4.2** |
| `GET /health` endpoint | **4.2**, **6.1** |
| `404` vs `422` distinction for unknown `product_id` | **4.2** |
| Catalog: `PRODUCT_IDS` / `PRODUCT_ID` / default five SKUs | **3.2**, **6.4** |
| Random `product_id` among non-exhausted SKUs | **3.1**, **5.1** |
| Environment variable table | **6.4** |
| TTL / validity during critical section | **3.3**, **3.4** |
| Consumer back-off on failed acquire | **3.4**, **5.1** |
| `MAX_CONSECUTIVE_ACQUIRE_FAILURES` / exit code | **3.4**, **5.1**, **6.4** |
| Consumer behaviour on unreachable inventory server | **3.4** |
| Consumer exit when all SKUs exhausted (409 per SKU) | **3.4**, **5.1** |
| Post-purchase delay **\[0 s, 3 s\]** after HTTP 200 | **5.1** |
| `MAX_ITERATIONS` for CI stability | **5.1**, **6.4** |
| `MAX_ITERATIONS` ceiling log / exit code | **5.1** |
| `CONSUMER_ID` in every log line | **5.2**, **6.2** |
| `ACQUIRE_RETRY_DELAY_MS_MIN/MAX` | **3.4**, **6.4** |
| Wrong-token release test (T-02) | **2.1**, **7.2** |
| T-04 concurrency assertion (≥ 2 distinct consumer IDs) | **2.1**, **7.2** |
| Test infrastructure: real Valkey, no fakeredis for Lua | **7.1** |
| Redlock implementation choice (hand-rolled vs library) | **6.3** |
| Compose: three Valkey + server + consumers | **6** |
| Compose resource limits | **6.1** |
| Logging consumed / exhausted | **4.3**, **5.2** |
| Pedagogic README | **8** |
| Redlock production caveats (README) | **3.5**, **8** |
| No-oversell: **`P × INITIAL_STOCK`** when fully sold out | **2.1**, **7.2** |

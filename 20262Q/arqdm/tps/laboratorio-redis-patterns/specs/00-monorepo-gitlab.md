# Specification: Monorepo structure for GitLab

**Version:** 0.1  
**Scope:** Repository layout, per-service build and test, and documentation.  
**Out of scope:** Delivery of artifacts, release pipelines, and any deployment to environments. Behavioral compliance checks beyond structural assertions (container builds, in-service test execution) — see Section 8.

This document is the foundational specification for organizing Python services in a single repository intended to run on GitLab (for example, GitLab CI). It defines where code lives, how each service is built and tested in isolation, and what product documentation must exist under `./docs`.

---

## 1. Goals

- Host multiple **Python** services in one repository without coupling their build or test lifecycles.
- Allow **each service to be built independently** from its own directory.
- Standardize **testing with pytest** inside containers, so local and CI behavior stay aligned.
- Keep **deployment and artifact publishing** undefined here; consumers may add GitLab CI jobs later without this spec prescribing them.

---

## 2. Repository layout

| Path | Purpose |
|------|---------|
| `./services/<service-name>/` | One deployable or runnable service; **the folder name is the canonical service name**. |
| `./docs/` | Human-facing documentation for monorepo practices (see Section 6). |
| `./specs/` | Normative specifications (including this file). |

Rules:

- **Service identity:** `service-name` MUST be the directory name immediately under `./services/`. The same string SHOULD be used consistently in Docker Compose project naming, image names, and documentation when referring to the service.
- **Naming rules:** `service-name` MUST match the pattern `[a-z][a-z0-9-]+` (lowercase ASCII letter, followed by one or more lowercase ASCII letters, digits, or hyphens). The maximum length is 40 characters. Names MUST be unique across all directories under `./services/`.
- **No shared “runtime” package requirement:** Services MAY share patterns or copy small snippets, but each service MUST remain a self-contained Python project (own dependency file, own tests, own Compose file) unless a future spec introduces a governed shared library.

---

## 3. Per-service Python project

All services in this repository MUST use **Python 3.11 or later**. The exact minor version SHOULD be pinned per service (for example via a `.python-version` file or a base image tag) and documented in the service's own README.

Each `./services/<service-name>/` directory MUST contain at least:

- A **Python dependency manifest** appropriate to the toolchain in use (for example `pyproject.toml` and/or `requirements.txt`). The spec does not mandate a single format; the service’s documentation MUST state how to install dependencies for dev and for the container images.
- **Application or library code** under a clear package or module layout (convention left to the service; documented in `./docs` patterns).
- **Tests** discoverable by **pytest** (for example under `tests/` with `test_*.py` or configured via `pyproject.toml`).

**Pytest configuration:** Services MUST declare pytest settings in **`pyproject.toml`** using the `[tool.pytest.ini_options]` table (per [pytest’s configuration](https://docs.pytest.org/en/stable/reference/customize.html#pyproject-toml)). That includes options that affect discovery (for example `testpaths`), markers, and default CLI options. Services MUST NOT use **`pytest.ini`** or **`tox.ini`** as the primary pytest configuration file; pytest configuration MUST live only in **`pyproject.toml`** as above.

> **Design rationale — pytest configuration only in `pyproject.toml`:**
> Contemporary Python projects already consolidate project metadata and tool settings in **`pyproject.toml`** (PEP 518/621). Keeping pytest under **`[tool.pytest.ini_options]`** gives a **single source of truth** next to dependencies and packaging, so discovery paths, markers, and defaults do not diverge from a separate **`pytest.ini`** or a pytest section **`tox.ini`**. That reduces duplicate or conflicting config, matches how other common tools are usually configured in the same file, and makes onboarding predictable (“open `pyproject.toml`”). The rule targets the **primary** pytest configuration file; it does not forbid auxiliary files for other tools, but pytest’s authoritative settings MUST remain in **`pyproject.toml`**.

Each service MUST be buildable and testable **without** building or testing sibling services.

---

## 4. Docker Compose per service

This spec requires **Docker Compose v2** (the `docker compose` plugin, not the legacy `docker-compose` standalone binary). All commands and examples use the `docker compose` subcommand form. The minimum required version is **Docker Compose 2.20**.

Each service MUST include a **`docker-compose.yaml`** (or `docker-compose.yml`) at:

`./services/<service-name>/docker-compose.yaml`

> **Design rationale — per-service file vs. root-level Compose with profiles:**
> A single root `docker-compose.yaml` with profiles would allow orchestrating all services from one place, but it couples every service's definition into a shared file. Any change to one service's Compose config becomes a change to a shared artifact, creating merge conflicts and making independent service evolution harder. A per-service file keeps each service's build and test environment fully self-contained: a developer or CI job can `cd services/<name>` and run commands without awareness of any other service. The trade-off is that cross-service orchestration (e.g., running all services together for local integration) requires a separate root-level Compose file or manual composition — that concern is explicitly out of scope for this spec.

> **Design rationale — Compose as the shared contract for local and CI:**
> Defining **`builder`** and **`test`** in Compose makes **build and test entry points explicit and repeatable**: images, working directories, env vars, and auxiliary services (databases, Redis, etc.) are declared once. A developer running **`docker compose run`** and a pipeline that **`cd`s into the same service directory and runs the same commands** exercise the same contract, which limits “passes locally, fails in CI” drift from different Python installs, OS packages, or forgotten setup steps on runners. The trade-off is that **Docker (or a compatible runtime) is required** for a faithful build/test; the spec accepts that cost in exchange for alignment between environments.

The Compose file MUST define **at least** these two services:

### 4.1 `builder`

- **Purpose:** Build the **runtime or distributable image** for this service (the image that represents “the service” for local runs or future CI stages outside this spec).
- **Responsibilities:** Install dependencies and bake application code according to the service’s `Dockerfile` (or equivalent). The exact base image and stages are service-specific but MUST be documented.

### 4.2 `test`

- **Purpose:** Run **pytest** in an environment that has **all code and test dependencies** needed to execute the suite.
- **Responsibilities:** Mount or copy the service source (including tests), install dev/test extras if required, and default to a command that runs pytest (for example `pytest` with appropriate paths or config). The image MAY be separate from `builder` or reuse a target; that choice MUST be documented for the service.

Additional Compose services (databases, Redis, message brokers) are allowed for integration tests but are not required by this spec.

**Naming:** Compose `service` keys SHOULD incorporate or clearly map to `<service-name>` in image names or container names to avoid clashes when multiple stacks run on one machine.

---

## 5. GitLab alignment (non-prescriptive)

This spec does **not** define `.gitlab-ci.yml` jobs. It is written so that GitLab CI can:

- Trigger jobs **per path** under `./services/<service-name>/`.
- In each job, `cd` to that directory and run `docker compose run --rm builder` and `docker compose run --rm test` (or equivalent) so that **CI mirrors local** build and test.

Any future CI configuration SHOULD treat each `./services/<service-name>/` as an independent unit of work.

---

## 6. Documentation under `./docs`

The repository MUST maintain documentation in **`./docs`** that explains monorepo-wide practices. At minimum, the following concepts MUST be covered (as separate pages or sections):

### 6.1 How to create a new service

- Choose `./services/<service-name>/` (naming rules, allowed characters, uniqueness).
- Scaffold a Python project (dependency file, source layout, pytest layout; **`pyproject.toml`** MUST include **`[tool.pytest.ini_options]`** for pytest).
- Add `Dockerfile`(s) and `docker-compose.yaml` with `builder` and `test` services.
- Verify locally: build image(s) and run tests via Compose.

### 6.2 How to set up the building process

- Relationship between `Dockerfile` and the Compose `builder` service.
- How to build only one service from the repo root or from the service directory.
- How the **folder name** maps to service name and image naming conventions.
- What “independent build” means (no dependency on building another service first).

### 6.3 How to run the test process

- How the Compose `test` service runs pytest.
- That pytest configuration for each service MUST be defined in **`pyproject.toml`** (`[tool.pytest.ini_options]`), and how that affects discovery and defaults.
- How to pass extra pytest arguments (if supported).
- How to add or run tests that need extra Compose services (if applicable).

### 6.4 Foundational concepts (cross-cutting)

Documentation SHOULD also briefly define:

- **Monorepo boundaries:** what belongs in `./services/` vs shared docs/specs.
- **pytest** as the standard test runner; configuration MUST be in each service’s **`pyproject.toml`** (`[tool.pytest.ini_options]`).
- **Out of scope:** no deployment or artifact delivery guidance in this spec; pointers to future docs if added later.

---

## 7. Acceptance criteria (for compliance with this spec)

- Every service under `./services/` has its own Python project layout and pytest-discoverable tests; pytest configuration MUST live in **`pyproject.toml`** (`[tool.pytest.ini_options]`) and MUST NOT use **`pytest.ini`** or **`tox.ini`** as the primary pytest configuration.
- Every service has a Compose file with **`builder`** and **`test`** services as defined above.
- `./docs` exists and documents the three workflows in Section 6, plus foundational concepts in 6.4.
- No requirement in this spec assumes a global deployment step or published artifact.

**Compliance smoke test:** Compliance with the criteria in this spec MUST be verifiable by a pytest-based suite located in **`./tests/`** at the repository root. Running **`pytest tests/`** from the repo root MUST pass for the repository to be considered compliant with **this** specification.

The root suite MUST be **structural only**: it asserts file presence and naming rules (for example, that each directory under `./services/` follows the naming pattern, contains a `docker-compose.yaml`, and has a `pyproject.toml` with a `[tool.pytest.ini_options]` table). It MUST NOT execute container builds, run pytest inside services, or require any live service or network. This keeps the compliance gate fast, self-contained, and free of external dependencies.

---

## 8. Future work (explicitly not part of this spec)

- GitLab CI/CD pipeline YAML, container registries, environments, and release strategy.
- Shared internal packages or a single lockfile for the whole monorepo.
- Production monitoring, secrets management, and infrastructure as code.
- Behavioral compliance checks (container builds, in-service test execution) if governance needs expand beyond structural assertions.

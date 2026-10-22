# Specification: GitLab CI for services (cloud)

**Version:** 0.3  
**Depends on:** [specs/00-monorepo-gitlab.md](00-monorepo-gitlab.md), [docs/monorepo-practices.md](../docs/monorepo-practices.md)  
**Scope:** Continuous integration on **GitLab.com** (or GitLab Dedicated / self-managed with SaaS-like runners): pipeline layout, jobs, and how they mirror local build and test.  
**Out of scope:** Deployment, release tagging, container registry publishing, environments, secrets rotation, and infrastructure as code beyond what CI needs to run jobs.

---

## 1. Goals

- Run **the same build and test contract as locally** for each service: from `services/<service-name>/`, execute Docker Compose **`builder`** then **`test`** (see Section 4 of the monorepo spec).
- Keep the monorepo **efficient**: avoid rebuilding every service on every commit when only one path changed; still allow a **full matrix** when appropriate (e.g. default branch or manual pipeline).
- Run the repository root **structural compliance** suite (`pytest tests/` from the repo root) on every relevant pipeline — fast, no Docker, no network — as defined in Section 7 of the monorepo spec.
- Use **GitLab CI/CD** features that work on **cloud-hosted GitLab** without assuming private runner fleets unless documented as an optional extension.

---

## 2. Platform assumptions

| Assumption | Notes |
|------------|--------|
| **GitLab** | GitLab.com or equivalent (minimum **GitLab 15.0**); `.gitlab-ci.yml` at repository root. `rules:changes` behavior and merge-request pipeline semantics prior to 15.0 differ materially and are not supported by this spec. |
| **Runners** | Shared Linux runners with **Docker** available (e.g. GitLab.com SaaS `docker` executor), or runners that can run `docker compose` (see Section 5). |
| **Compose** | **Docker Compose v2** (`docker compose` plugin), minimum **v2.1** (required for reliable `--rm` exit-code propagation), consistent with the monorepo spec. |
| **Repository layout** | Services live under `./services/<service-name>/` per `00-monorepo-gitlab.md`. |

The pipeline MUST NOT require a specific GitLab tier for core behavior; optional features (e.g. advanced caching, compliance scanning) MAY be documented as follow-ups.

---

## 3. Pipeline stages (normative)

Stages SHOULD be ordered as follows (names may vary; semantics MUST hold):

1. **`lint` or `validate`** — Run root structural compliance: `pytest tests/` from the repository root (no service containers). Fails fast if layout or required files violate the monorepo spec.
2. **`build`** — For each selected service, run `docker compose run --rm builder` from `services/<service-name>/`.
3. **`test`** — For each selected service, run `docker compose run --rm test` from `services/<service-name>/`.

**Ordering:** Structural validation MUST complete before service jobs start. Prefer failing compliance early rather than spending runner time on Docker builds when the layout is already broken. Implementations MAY collapse all jobs into a single stage provided `needs:` entries ensure compliance finishes first.

**`build` → `test` dependency:** The `test` job for service `S` MUST declare `needs: [build:S]` (or the equivalent for the chosen job-naming scheme) so that (a) it does not start before the `builder` finishes, and (b) the Docker images produced by `builder` are present on the same runner. If `build` and `test` run on different runner instances with no shared Docker daemon, the implementation MUST export and import the built images via a GitLab artifact (`docker save` / `docker load`) or use a registry as intermediary. When using `docker save`, the `build` job MUST save **all** images needed to run `docker compose run --rm test` — including the `test` service image if it is built from a separate `Dockerfile` stage — not only the runtime image produced by `builder`. Prefer `docker save`/`docker load` for simplicity; prefer a registry when image size exceeds the GitLab artifact size limit for the project's tier (GitLab Free caps job artifacts at 1 GB by default). A `test` job that silently rebuilds what `builder` already built does **not** satisfy this spec.

**Branch protection:** On protected branches, pipelines SHOULD require the compliance job and all executed service jobs to pass before merge (policy configured in GitLab project settings, not only in YAML).

---

## 4. Monorepo path rules

GitLab **`rules:changes`** (or equivalent) MUST drive which `services/*` jobs run:

- **Default:** If files under `services/<name>/` change, include jobs for that `<name>`.
- **Shared / root changes:** If any of the following change, run **all** service jobs (or at minimum all services that exist under `./services/`), because they may affect every service:
  - `.gitlab-ci.yml`
  - Root `tests/` (structural compliance)
  - `specs/` or `docs/` when the project chooses to treat them as affecting verification (recommended: trigger full service matrix when `specs/` changes; optional for `docs/` only).
- **Compliance-only changes:** Changes confined to paths that do not affect services MAY still run the root `pytest tests/` job; service Docker jobs MAY be skipped if no service path changed (project policy).

**New-service bootstrap:** `rules:changes` compares against a base SHA and will produce **no diff** for a brand-new `services/<name>/` directory on its first commit (there is no previous tree to compare). Implementations MUST handle this by including one of:
- A fallback rule that runs all **existing** service jobs when `rules:changes` produces no matches (e.g. `when: always` as a final `rules` entry guarded on `$CI_PIPELINE_SOURCE != "schedule" && $CI_PIPELINE_SOURCE != "trigger"`), **or**
- A requirement that adding a new service always accompanies a change to `.gitlab-ci.yml` (triggering the shared-changes full-matrix rule above), documented explicitly in developer guidance.

**Important constraint for static matrices:** a fallback rule only fires _existing_ job definitions. A brand-new service `C` has no `build:C` / `test:C` jobs until they are added to `.gitlab-ci.yml`. Therefore, with a static matrix, adding service `C` always requires new job entries in `.gitlab-ci.yml` — the zero-SHA bootstrap rule alone is not sufficient. Developer guidance MUST state this explicitly so the requirement to update `.gitlab-ci.yml` is not overlooked.

The chosen approach MUST be documented in `./docs`.

**Discovery:** The list of services MAY be maintained explicitly in CI variables or generated (e.g. a script that lists `./services/*` directories). Dynamic child pipelines or generated YAML are optional; a static matrix with rules is acceptable for small monorepos (typically fewer than ~10 services). A static matrix is simpler to reason about and requires no extra CI infrastructure; the trade-off is that each new service requires a manual edit to `.gitlab-ci.yml`. Dynamic child pipelines remove that manual step but add complexity (a generation script, an extra pipeline layer, and potential differences in `needs:` and artifact handling). If a static matrix is used, the developer workflow MUST include a step to update the matrix when a new service is added; this MUST be noted in `./docs`.

---

## 5. Docker on runners

Service jobs need a environment where **`docker compose`** can build and run containers. Acceptable patterns:

1. **`docker` executor with Docker socket / Docker-in-Docker (DinD)** — Use the pattern documented for the chosen GitLab version so `docker compose` inside the job can build images. Pin or document the Compose plugin version if needed. When using DinD, the job MUST wait for the daemon to be ready before issuing any `docker` command; a common idiom is `until docker info; do sleep 2; done` in `before_script`. Omitting this wait causes intermittent failures when the daemon has not yet accepted connections.
2. **Shell executor on a Docker-capable host** — Less common on SaaS; document if used.

The spec does not mandate DinD vs socket mounting; it mandates that **`docker compose run --rm builder`** and **`docker compose run --rm test`** succeed in CI when they succeed locally for the same commit.

**Image:** CI job image SHOULD include Docker CLI and Compose v2, or use GitLab’s recommended DinD service image pair for the project’s GitLab version.

---

## 6. Job commands (per service)

For each service `S` at `services/S/`:

```bash
cd "services/S"
docker compose run --rm builder
docker compose run --rm test
```

Exact quoting and working directory MUST match the service layout. If a service requires `docker compose up -d` for dependencies (Redis, DB), those services MUST be declared in the **same** `docker-compose.yaml` and started in the same job (or a prior job) so that **`docker compose run --rm test`** is sufficient, per [docs/monorepo-practices.md](../docs/monorepo-practices.md).

---

## 7. Root compliance job

From repository root:

```bash
pytest tests/
```

Requirements:

- Use the **same Python version** as documented for root tooling (e.g. 3.12) or the version available on the runner, documented in the service/README or CI doc if discrepancies matter.
- Install test dependencies with **minimal** footprint (e.g. `pip install pytest` only if `tests/` has no extra deps; align with root `pyproject.toml` if present).

This job MUST remain **structural** (no container builds, no in-service pytest), per Section 7 of `00-monorepo-gitlab.md`.

---

## 8. Caching and performance

Docker layer caching on GitLab.com shared runners is **not persistent across jobs** unless explicitly configured. Implementations MUST choose one of the following documented approaches and note the choice in `./docs`:

- **No layer cache (default acceptable):** Accept that each job pulls base images fresh. Simple and correct; images are rebuilt from scratch each run. Required footprint: base image pull time only. Note: if `build` and `test` run on separate runners and images are transferred via `docker save` artifact, verify that the saved tarball fits within the project's GitLab artifact size limit (GitLab Free defaults to 1 GB per job). Large base images (e.g. `python:3.12`) can easily approach this limit; switch to a registry-based approach or enable the Dependency Proxy if image tarballs grow too large.
- **GitLab Dependency Proxy:** Pull-through cache for public Docker Hub images. Reduces pull time and rate-limit exposure. Enable per project; requires `DOCKER_HUB_*` proxy URL substitution in `docker-compose.yaml` or compose override.
- **`cache:` on pip/npm/etc. artifacts only:** Cache package manager directories between runs where build speed matters. Does **not** cache Docker layers.

Advanced Docker layer caching (e.g. `--cache-from` with a registry) is deferred to Section 13 (future work).

**Concurrency:** Implementations SHOULD use `resource_group` per service if jobs on the same branch can queue behind each other, to avoid Docker daemon conflicts on shell-executor runners. On DinD, concurrency is isolated by default.

---

## 9. Variables and secrets

- **No secrets required** for the minimal spec (public deps, no registry push).
- If later jobs add registry login or API keys, use **GitLab CI/CD variables** (masked, protected) and document them in project docs — not in this spec’s required path.

---

## 10. Merge request and default-branch behavior

- **Merge request pipelines:** MUST run structural compliance; MUST run `builder` + `test` for every service with paths changed in the MR (and for full matrix when shared files change per Section 4).
- **Default branch (e.g. `main`):** SHOULD run full compliance and SHOULD run all services’ `builder` and `test` jobs at least on each push, or on a schedule, so integration drift is caught even when a change looks root-only.

Exact policy MAY be tuned for cost vs coverage; document the choice in `./docs` or a short `README` section at repo root.

---

## 11. Artifacts and failure visibility

- **Test reports:** Optionally expose JUnit from pytest via GitLab’s `reports:junit` for MR widgets (requires pytest configuration to emit JUnit XML in service `test` jobs or a wrapper script).
- **Logs:** On failure, job logs MUST clearly show which `services/<name>` failed and whether `builder` or `test` failed.

---

## 12. Acceptance criteria (this spec)

The implementation is compliant when:

1. `.gitlab-ci.yml` (or included files) exists at the repository root and the compliance job runs **`pytest tests/`** from the repo root, exits non-zero on structural failures, and runs on both MR and default-branch pipelines.
2. For each service under `./services/`, CI runs **`docker compose run --rm builder`** followed by **`docker compose run --rm test`** from that service’s directory, with `test` declared dependent on `build` via `needs:` (or equivalent), and the image produced by `builder` is available to `test` without being rebuilt.
3. **Path-based rules** prevent unrelated services from running when only one service’s tree changes (with full matrix when shared CI or spec paths change, per Section 4). Adding a new service triggers its jobs on the first commit, per the bootstrap rule in Section 4.
4. Documentation under `./docs` is updated with a short **”CI”** subsection pointing to this spec, describing how to interpret pipeline failures, and documenting the chosen caching approach and new-service bootstrap procedure.

**Verification recipe for criterion 3:** To validate path filtering works correctly:
- Open an MR that modifies only files under `services/A/` (for a repo with at least two services `A` and `B`).
- Confirm in the pipeline that jobs for service `B` show as **skipped** (not failed, not run).
- Confirm jobs for service `A` (`build:A`, `test:A`) run and pass.
- Then push a change to `.gitlab-ci.yml` or `specs/` in the same or a new MR and confirm **both** services’ jobs run.
- Finally, add a new `services/C/` directory in a commit and confirm that `build:C` and `test:C` run (bootstrap rule is exercised).

---

## 13. Future work (out of scope)

- Container registry push, image signing, SBOM, and deployment stages.
- Advanced Docker layer caching via `--cache-from` with a registry (requires registry push, deferred above).
- Scheduled security scans, DAST, or dependency review bots.
- Self-hosted runner fleets, Kubernetes executors, and autoscaling.

---

## 14. References

- [specs/00-monorepo-gitlab.md](00-monorepo-gitlab.md) — layout, `builder` / `test`, structural compliance.
- [docs/monorepo-practices.md](../docs/monorepo-practices.md) — developer workflows and Compose usage.

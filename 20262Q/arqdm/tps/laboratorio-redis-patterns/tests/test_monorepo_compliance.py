"""Structural compliance with specs/00-monorepo-gitlab.md (no Docker, no network)."""

from __future__ import annotations

import re
from pathlib import Path

import pytest

REPO_ROOT = Path(__file__).resolve().parents[1]
SERVICES_DIR = REPO_ROOT / "services"

SERVICE_NAME_RE = re.compile(r"^[a-z][a-z0-9-]{0,39}$")


def _service_dirs() -> list[Path]:
    if not SERVICES_DIR.is_dir():
        return []
    return sorted(
        p for p in SERVICES_DIR.iterdir() if p.is_dir() and not p.name.startswith(".")
    )


def test_services_directory_exists() -> None:
    assert SERVICES_DIR.is_dir(), "Expected ./services/ to exist"


@pytest.mark.parametrize(
    "path",
    _service_dirs(),
    ids=lambda p: p.name,
)
def test_each_service_matches_layout_rules(path: Path) -> None:
    name = path.name
    assert SERVICE_NAME_RE.match(name), (
        f"Service folder name must match [a-z][a-z0-9-]+ (max 40 chars): {name!r}"
    )

    compose = path / "docker-compose.yaml"
    alt = path / "docker-compose.yml"
    assert compose.is_file() or alt.is_file(), (
        f"Expected docker-compose.yaml or docker-compose.yml under {path}"
    )

    pyproject = path / "pyproject.toml"
    assert pyproject.is_file(), f"Expected pyproject.toml under {path}"

    text = pyproject.read_text(encoding="utf-8")
    assert "[tool.pytest.ini_options]" in text, (
        f"Expected [tool.pytest.ini_options] in {pyproject}"
    )


def test_compose_files_define_builder_and_test() -> None:
    for svc in _service_dirs():
        compose = svc / "docker-compose.yaml"
        if not compose.is_file():
            compose = svc / "docker-compose.yml"
        raw = compose.read_text(encoding="utf-8")
        assert re.search(r"(?m)^\s{0,2}builder:\s*$", raw), (
            f"{compose} must define a top-level `builder` service"
        )
        assert re.search(r"(?m)^\s{0,2}test:\s*$", raw), (
            f"{compose} must define a top-level `test` service"
        )


def test_ci_prepare_script_exists() -> None:
    script = REPO_ROOT / "ci" / "prepare_child_pipeline.py"
    assert script.is_file(), (
        "Expected ci/prepare_child_pipeline.py for dynamic child pipeline"
    )

"""Unit tests for ci/prepare_child_pipeline.py service selection (no git, no network)."""

from __future__ import annotations

import importlib.util
from pathlib import Path

import pytest

REPO_ROOT = Path(__file__).resolve().parents[1]


def _load_prepare_module():
    path = REPO_ROOT / "ci" / "prepare_child_pipeline.py"
    spec = importlib.util.spec_from_file_location("prepare_child_pipeline", path)
    assert spec and spec.loader
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


@pytest.fixture(scope="module")
def prep():
    return _load_prepare_module()


def test_select_services_default_branch_runs_all(prep) -> None:
    # Given
    all_svc = ["alpha", "beta"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=True,
        bootstrap_first_commit=False,
        changed_paths=[],
    )
    # Then
    assert got == ["alpha", "beta"]


def test_select_services_bootstrap_commit_runs_all(prep) -> None:
    # Given
    all_svc = ["alpha", "beta"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=False,
        bootstrap_first_commit=True,
        changed_paths=[],
    )
    # Then
    assert got == ["alpha", "beta"]


def test_select_services_shared_gitlab_ci_runs_all(prep) -> None:
    # Given
    all_svc = ["a", "b"]
    changed = ["services/a/foo.txt", ".gitlab-ci.yml"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=False,
        bootstrap_first_commit=False,
        changed_paths=changed,
    )
    # Then
    assert got == ["a", "b"]


def test_select_services_shared_specs_runs_all(prep) -> None:
    # Given
    all_svc = ["x", "y"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=False,
        bootstrap_first_commit=False,
        changed_paths=["specs/01-gitlab-service-ci.md"],
    )
    # Then
    assert got == ["x", "y"]


def test_select_services_shared_ci_dir_runs_all(prep) -> None:
    # Given
    all_svc = ["x", "y"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=False,
        bootstrap_first_commit=False,
        changed_paths=["ci/prepare_child_pipeline.py"],
    )
    # Then
    assert got == ["x", "y"]


def test_select_services_only_paths_under_one_service(prep) -> None:
    # Given
    all_svc = ["demo-service", "redis-cache-aside"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=False,
        bootstrap_first_commit=False,
        changed_paths=["services/redis-cache-aside/README.md"],
    )
    # Then
    assert got == ["redis-cache-aside"]


def test_select_services_unrelated_root_files_select_none(prep) -> None:
    # Given
    all_svc = ["demo-service", "redis-cache-aside"]
    # When
    got = prep.select_services(
        all_services=all_svc,
        is_default_branch=False,
        bootstrap_first_commit=False,
        changed_paths=["README.md", "docs/monorepo-practices.md"],
    )
    # Then
    assert got == []


def test_render_child_pipeline_empty_emits_noop(prep) -> None:
    # Given / When
    yml = prep.render_child_pipeline([])
    # Then
    assert "no-service-changes" in yml
    assert "stages:" in yml
    # Clean — nothing persisted


def test_render_child_pipeline_includes_build_and_test_jobs(prep) -> None:
    # Given
    services = ["demo-service"]
    # When
    yml = prep.render_child_pipeline(services)
    # Then
    assert '"build:demo-service":' in yml
    assert '"test:demo-service":' in yml
    assert "docker compose run --rm test" in yml

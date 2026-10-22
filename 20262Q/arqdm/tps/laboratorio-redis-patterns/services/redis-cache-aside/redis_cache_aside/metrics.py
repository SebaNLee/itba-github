from __future__ import annotations

import threading
from dataclasses import asdict, dataclass


@dataclass
class MetricsSnapshot:
    cache_hits: int
    cache_misses: int
    db_reads: int
    cache_write_failures: int


class Metrics:
    """Process-wide counters for demos and tests (spec §5.2)."""

    def __init__(self) -> None:
        self._lock = threading.Lock()
        self.cache_hits = 0
        self.cache_misses = 0
        self.db_reads = 0
        self.cache_write_failures = 0

    def reset(self) -> None:
        with self._lock:
            self.cache_hits = 0
            self.cache_misses = 0
            self.db_reads = 0
            self.cache_write_failures = 0

    def snapshot(self) -> MetricsSnapshot:
        with self._lock:
            return MetricsSnapshot(
                cache_hits=self.cache_hits,
                cache_misses=self.cache_misses,
                db_reads=self.db_reads,
                cache_write_failures=self.cache_write_failures,
            )

    def record_hit(self) -> None:
        with self._lock:
            self.cache_hits += 1

    def record_miss(self) -> None:
        with self._lock:
            self.cache_misses += 1

    def record_db_read(self) -> None:
        with self._lock:
            self.db_reads += 1

    def record_cache_write_failure(self) -> None:
        with self._lock:
            self.cache_write_failures += 1

    def as_dict(self) -> dict[str, int]:
        return asdict(self.snapshot())


_metrics = Metrics()


def get_metrics() -> Metrics:
    return _metrics

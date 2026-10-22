"""CLI: inventory-server | inventory-consumer."""

from __future__ import annotations

import argparse
import logging
import os

import uvicorn

from distributed_lock import __version__
from distributed_lock.consumer import run_consumer
from distributed_lock.inventory_app import create_app


def _configure_logging() -> None:
    level = os.environ.get("LOG_LEVEL", "INFO").upper()
    logging.basicConfig(
        level=getattr(logging, level, logging.INFO),
        format="%(levelname)s %(name)s %(message)s",
    )


def main() -> None:
    parser = argparse.ArgumentParser(prog="distributed-lock")
    parser.add_argument(
        "--version", action="version", version=f"%(prog)s {__version__}"
    )
    sub = parser.add_subparsers(dest="command", required=True)

    sub.add_parser("inventory-server", help="Run FastAPI inventory HTTP server")
    sub.add_parser("inventory-consumer", help="Run Redlock consumer loop")

    args = parser.parse_args()

    if args.command == "inventory-consumer":
        _configure_logging()
        raise SystemExit(run_consumer())

    _configure_logging()
    port = int(os.environ.get("PORT", "8000"))
    uvicorn.run(
        create_app(),
        host="0.0.0.0",
        port=port,
        workers=1,
        log_level=os.environ.get("UVICORN_LOG_LEVEL", "info"),
    )


if __name__ == "__main__":
    main()

from __future__ import annotations

import strawberry
from strawberry.extensions import FieldExtension
from strawberry.types import Info

from redis_cache_aside.catalog_service import CatalogService
from redis_cache_aside.product_model import ProductRow


@strawberry.type
class Product:
    id: strawberry.ID
    title: str
    description: str
    price: float


def _to_gql(row: ProductRow | None) -> Product | None:
    if row is None:
        return None
    return Product(
        id=strawberry.ID(row.id),
        title=row.title,
        description=row.description,
        price=row.price,
    )


class CacheAsideProductExtension(FieldExtension):
    """Single GraphQL hook for cache-aside reads (spec §3.1: middleware / extension)."""

    async def resolve_async(self, next_, source, info: Info, **kwargs):
        catalog: CatalogService = info.context["catalog"]
        product_id = str(kwargs["id"])
        return _to_gql(await catalog.get_product(product_id))


@strawberry.type
class Query:
    @strawberry.field(extensions=[CacheAsideProductExtension()])
    async def product(self, id: strawberry.ID) -> Product | None:
        return None


schema = strawberry.Schema(query=Query)

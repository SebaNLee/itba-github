-- Seed schema + sample rows for the cache-aside lab (spec §3.1, §7).
CREATE TABLE IF NOT EXISTS products (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

INSERT INTO products (id, title, description, price) VALUES
    (
        'demo-1',
        'Demo Product',
        'Seeded catalog row for local runs and CI.',
        19.99
    ),
    (
        'hot-sku',
        'Hot SKU',
        'Read-heavy SKU used in the demo script narrative.',
        49.00
    )
ON CONFLICT (id) DO NOTHING;

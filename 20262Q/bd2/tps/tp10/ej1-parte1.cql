
-- ej1, parte 1

DROP KEYSPACE IF EXISTS demo_cql_music;

-- 1.
CREATE KEYSPACE demo_cql_music WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};

-- 2.
USE demo_cql_music;

-- 3.
CREATE TABLE canciones (
    id uuid PRIMARY KEY,
    titulo text,
    album text,
    artista text,
    data blob
);

-- 4.
INSERT INTO canciones (id, titulo, album, artista) VALUES (7db1a490-5878-11e2-bcfd-0800200c9a66, 'Asilo', 'Salvavidas de Hielo', 'Jorge Drexler');

-- 5.
CREATE TABLE lista_reproduccion (
    id UUID, -- clave partición
    nro_cancion INT, -- número de la canción en la playlist, "orden"
    cancion_id UUID, -- clave clustering
    titulo TEXT,
    album TEXT,
    artista TEXT,
    PRIMARY KEY (id, nro_cancion)
);

-- 6.
INSERT INTO lista_reproduccion (id, nro_cancion, cancion_id, titulo, artista, album) VALUES (62c36092-82a1-3a00-93d1-46196ee77204, 1,7db1a490-5878-11e2-bcfd-0800200c9a66, 'Asilo','Jorge Drexler','Salvavidas de Hielo');

-- 7.
-- salto, es usar now() para el INSERT INTO ... VALUES ..., y luego:
SELECT id, titulo FROM canciones; -- para sacar UUIDs generados y usarlos para INSERTs a lista_reproduccion
-- luego para check:
SELECT * FROM lista_reproduccion;

-- 8.
SELECT * FROM lista_reproduccion
WHERE id=62c36092-82a1-3a00-93d1-46196ee77204
ORDER BY nro_cancion DESC;

-- 9.
-- SELECT * FROM lista_reproduccion WHERE artista='Jorge Drexler';
-- message="Cannot execute this query as it might involve data filtering and thus may have unpredictable performance. If you want to execute this query despite the performance unpredictability, use ALLOW FILTERING"

-- 10.
SELECT * FROM lista_reproduccion WHERE artista='Jorge Drexler' ALLOW FILTERING;
-- con ALLOW FILTERING se recorre secuencialmente, por eso anda, pero es super ineficiente

-- 11.
-- como alternativa, se podría crear un índice
CREATE INDEX indice_artista ON lista_reproduccion(artista);
-- SELECT * FROM lista_reproduccion WHERE artista='Jorge Drexler'; -- error porque CREATE INDEX es asíncrono, no llega a crearse para esta consulta (ejecutar aparte) (lo ejecuto en 13. también, que sí funciona (ojo race condition))
-- sigue siendo más lento que buscar por ID igualmente (clave de partición)

-- 12.
ALTER TABLE lista_reproduccion ADD etiquetas SET<text>;

-- 13.
UPDATE lista_reproduccion SET etiquetas = etiquetas + {'2017'} WHERE id = 62c36092-82a1-3a00-93d1-46196ee77204 AND nro_cancion = 1;
UPDATE lista_reproduccion SET etiquetas = etiquetas + {'guitarra'} WHERE id = 62c36092-82a1-3a00-93d1-46196ee77204 AND nro_cancion = 1;
UPDATE lista_reproduccion SET etiquetas = etiquetas + {'Mon Laferte'} WHERE id = 62c36092-82a1-3a00-93d1-46196ee77204 AND nro_cancion = 1;

SELECT * FROM lista_reproduccion WHERE artista='Jorge Drexler';

-- 14.
CREATE INDEX indice_etiquetas ON lista_reproduccion (etiquetas);
-- SELECT * FROM lista_reproduccion WHERE etiquetas CONTAINS 'guitarra'; -- misma caca async de antes (11.)

-- 15.
DELETE FROM lista_reproduccion WHERE id = 62c36092-82a1-3a00-93d1-46196ee77204 AND nro_cancion = 1;
SELECT * FROM lista_reproduccion;

-- 16.
DELETE FROM lista_reproduccion WHERE id = 62c36092-82a1-3a00-93d1-46196ee77204;
SELECT * FROM lista_reproduccion;

-- 17.
DROP TABLE lista_reproduccion;

-- 18.
DROP KEYSPACE demo_cql_music;


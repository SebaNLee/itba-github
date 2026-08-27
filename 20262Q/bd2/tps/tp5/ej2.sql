
-- https://dev.mysql.com/doc/refman/9.7/en/index-btree-hash.html
-- 
-- Hash Index Characteristics
-- Hash indexes have somewhat different characteristics from those just discussed:
-- They are used only for equality comparisons that use the = or <=> operators (but are very fast). They are not used for comparison operators such as < that find a range of values. Systems that rely on this type of single-value lookup are known as “key-value stores”; to use MySQL for such applications, use hash indexes wherever possible.
-- The optimizer cannot use a hash index to speed up ORDER BY operations. (This type of index cannot be used to search for the next entry in order.)
-- MySQL cannot determine approximately how many rows there are between two values (this is used by the range optimizer to decide which index to use). This may affect some queries if you change a MyISAM or InnoDB table to a hash-indexed MEMORY table.
-- Only whole keys can be used to search for a row. (With a B-tree index, any leftmost prefix of the key can be used to find rows.)
-- DROP TABLE IF EXISTS materia;

DROP TABLE IF EXISTS materia;

CREATE TABLE materia (
    codigo INT,
    nombre VARCHAR(40)
);

INSERT INTO materia(codigo, nombre) VALUES
    (10, 'Introduccion a la Computacion'),
    (20, 'Programacion I'),
    (30, 'Estructura de Datos y Algoritmos'),
    (40, 'Base de Datos I'),
    (50, 'Programacion IV'),
    (60, 'Base de Datos II');

SET SESSION explain_format = 'TREE';

-- a)
-- i)
ALTER TABLE materia ADD PRIMARY KEY (codigo);

EXPLAIN ANALYZE SELECT nombre
FROM materia
WHERE codigo = 10;
-- index lookup por ser PK

-- ii)
ALTER TABLE materia DROP PRIMARY KEY;
ALTER TABLE materia ADD PRIMARY KEY (codigo, nombre);

EXPLAIN ANALYZE SELECT nombre
FROM materia
WHERE codigo = 10;
-- sigue siendo index lookup por ser PK

-- iii)


-- iv)

-- b)
-- i)
EXPLAIN ANALYZE SELECT nombre
FROM materia
WHERE codigo = 60 and nombre = 'Base de Datos II';
-- index lookup

-- ii)
ALTER TABLE materia ADD CONSTRAINT PRIMARY KEY(codigo, nombre);

EXPLAIN ANALYZE SELECT nombre
FROM materia
WHERE codigo = 60 and nombre = 'Base de Datos II';
-- idem

-- iii)
ALTER TABLE materia DROP PRIMARY KEY;
CREATE UNIQUE INDEX uq_codigo ON materia(codigo);
CREATE UNIQUE INDEX uq_nombre ON materia(nombre);

EXPLAIN ANALYZE SELECT nombre
FROM materia
WHERE codigo = 60 and nombre = 'Base de Datos II';
--idem 


-- i)
-- ii)
-- iii)
-- iv)


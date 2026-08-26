
-- https://dev.mysql.com/doc/refman/9.7/en/explain.html#explain-execution-plan

DROP TABLE IF EXISTS materia;

CREATE TABLE materia(
    codigo INT PRIMARY KEY,
    nombre VARCHAR(40)
);

INSERT INTO materia(codigo, nombre) VALUES
    (10, 'Introduccion a la Computacion'),
    (20, 'Programacion I'),
    (30, 'Estructura de Datos y Algoritmos'),
    (40, 'Base de Datos I'),
    (50, 'Programacion IV'),
    (60, 'Base de Datos II'
);

SELECT * FROM materia;

-- simplemente cambia el formato de los EXPLAIN (incluye costo, row,s loops, etc)
SET @@explain_format=TREE;

-- table scan, al ser sin WHERE. Estima y no corre
EXPLAIN SELECT * FROM materia;

-- idem, pero esta vez sí ejecuta
EXPLAIN ANALYZE SELECT * FROM materia;

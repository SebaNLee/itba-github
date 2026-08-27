

-- copiar archivos al contenedor de docker MyMySql para que sql los vea
-- docker cp ./_source/materia.csv   MyMySql:/var/lib/mysql-files/materia.csv
-- docker cp ./_source/inscripto.csv MyMySql:/var/lib/mysql-files/inscripto.csv


DROP TABLE IF EXISTS materia;
DROP TABLE IF EXISTS inscripto;

CREATE TABLE materia(
    codigo INT,
    nombre VARCHAR(40)
);

CREATE TABLE inscripto (
    legajo INT,
    codigo INT
);

LOAD DATA INFILE '/var/lib/mysql-files/materia.csv'
INTO TABLE materia
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(codigo, nombre);

LOAD DATA INFILE '/var/lib/mysql-files/inscripto.csv'
INTO TABLE inscripto
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS (legajo, codigo);

-- consultas directas
SELECT 'CONSULTAS DIRECTAS:' AS '';

EXPLAIN ANALYZE
SELECT nombre
FROM materia
WHERE codigo = 2222;

EXPLAIN ANALYZE
SELECT *
FROM inscripto
WHERE codigo = 2222;

EXPLAIN ANALYZE
SELECT *
FROM materia m
JOIN inscripto i ON m.codigo = i.codigo;

-- consultas con índices
SELECT 'CONSULTAS CON ÍNDICES:' AS '';

-- se agregan PKs, entonces se agregan índices automáticamente
ALTER TABLE materia ADD PRIMARY KEY (codigo);
ALTER TABLE inscripto ADD PRIMARY KEY (legajo, codigo);

EXPLAIN ANALYZE
SELECT nombre
FROM materia
WHERE codigo = 2222;

EXPLAIN ANALYZE
SELECT *
FROM inscripto
WHERE codigo = 2222;

EXPLAIN ANALYZE
SELECT *
FROM materia m
JOIN inscripto i ON m.codigo = i.codigo;

-- observar que cambia de table scan a index scan

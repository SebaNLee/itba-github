
-- 1a)
SELECT razon_social
FROM entrega e
JOIN video v ON e.id_video = v.id_video
JOIN renglon_entrega r ON e.nro_entrega = r.nro_entrega
WHERE EXTRACT(YEAR FROM fecha_entrega) = 2022 AND codigo_pelicula IN (
    SELECT codigo_pelicula
    FROM pelicula
    WHERE idioma LIKE 'English'
);

-- 1b)
-- SELECT id_departamento, id_distribuidor, nombre_departamento
-- FROM departamento
-- WHERE 
-- ?? supongo que con SELECT MAX(sueldo)...

-- 1c)
SELECT SUM(cantidad)
FROM renglon_entrega r
JOIN entrega e ON r.nro_entrega = e.nro_entrega
JOIN nacional n ON e.id_distribuidor = n.id_distribuidor -- observar que nacional tiene el id, se saltea tabla distribuidor
WHERE EXTRACT(YEAR FROM fecha_entrega) = 2022;

-- 1d)
SELECT p.titulo
FROM pelicula p
WHERE p.codigo_pelicula NOT IN (
    SELECT r.codigo_pelicula
    FROM entrega e
    JOIN renglon_entrega r ON e.nro_entrega = r.nro_entrega
    JOIN nacional n ON n.id_distribuidor = e.id_distribuidor
);

-- 1e)
SELECT e.nombre, e.apellido
FROM empleado e
WHERE e.id_empleado IN (
    SELECT id_jefe
    FROM empleado e
    JOIN departamento d ON d.id_departamento = e.id_departamento
    WHERE d.id_ciudad IN (
        SELECT c.id_ciudad
        FROM ciudad c
        JOIN pais p ON c.id_pais = p.id_pais
        WHERE p.nombre_pais LIKE 'España' 
    )
);

-- 1f)
SELECT p.genero, SUM(r.cantidad) AS 'cantidad_total'
FROM renglon_entrega r
JOIN pelicula p ON p.codigo_pelicula = r.codigo_pelicula
GROUP BY p.genero
ORDER BY cantidad_total DESC;

-- 1g)
-- salto

-- 1h)
-- salto

-- 2)
DROP TABLE IF EXISTS DistribuidorNac;
CREATE TABLE DistribuidorNac
(
    id_distribuidor numeric(5,0) NOT NULL,
    nombre character varying(80) NOT NULL,
    direccion character varying(120) NOT NULL,
    telefono character varying(20),
    nro_inscripcion numeric(8,0) NOT NULL,
    encargado character varying(60) NOT NULL,
    id_distrib_mayorista numeric(5,0),
    CONSTRAINT pk_distribuidorNac PRIMARY KEY (id_distribuidor)
);

-- 2a)
INSERT INTO DistribuidorNac -- (id_distribuidor, nombre, direccion, telefono, nro_inscripcion, encargado, id_distrib_mayorista)
SELECT d.id_distribuidor, nombre, direccion, telefono, nro_inscripcion, encargado, id_distrib_mayorista
FROM distribuidor d JOIN nacional n ON d.id_distribuidor = n.id_distribuidor
WHERE tipo = 'N';

-- 2b)
ALTER TABLE DistribuidorNac
ADD COLUMN codigo_pais VARCHAR(5) NULL;

-- 2c)
UPDATE DistribuidorNac d
JOIN internacional i ON d.id_distribuidor = i.id_distribuidor
SET d.codigo_pais = i.codigo_pais;


SELECT * FROM DistribuidorNac;

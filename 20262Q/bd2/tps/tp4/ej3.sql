
-- a)
CREATE VIEW Distribuidor_200 AS
SELECT id_distribuidor, nombre, tipo
FROM distribuidor
WHERE id_distribuidor > 200;
-- se puede UPDATE y DELETE, pero no INSERT (porque falta un campo que es NOT NULL, y va a tratar de instartar con NULL)

CREATE VIEW Departamento_dist_200 AS
SELECT id_departamento, nombre_departamento, id_ciudad, jefe_departamento
FROM departamento
WHERE id_distribuidor > 200;
-- este tiene PK compuesta, y no está completa acá, entonces no modificable

-- b)
-- falla por integirdad restricción de integridad de unicidad

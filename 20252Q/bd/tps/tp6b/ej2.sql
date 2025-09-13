

-- 2.2
SELECT legajo
FROM inscripcion
GROUP BY legajo
HAVING COUNT(DISTINCT codigo) > 2


-- 2.3
-- ??


-- 2.4
INSERT INTO inscripcion VALUES(4, 30002, 2013);
INSERT INTO inscripcion VALUES(4, 30003, 2013);

-- 2.5
SELECT DISTINCT anio
FROM inscripcion AS i
WHERE NOT EXISTS (
    SELECT *
    FROM alumno, inscripcion
    WHERE sexo = 'F' AND alumno.legajo = inscripcion.legajo AND i.anio = inscripcion.anio
)

-- 2.6
SELECT anio
FROM inscripcion
GROUP BY anio
HAVING COUNT(anio) >= ALL (
    SELECT COUNT(anio)
    FROM inscripcion
    GROUP BY anio
)

-- 2.7
SELECT nombre, COUNT(*)
FROM alumno, inscripcion
WHERE alumno.legajo = inscripcion.legajo
GROUP BY nombre

-- 2.8
SELECT nombre, COUNT(DISTINCT codigo)
FROM alumno, inscripcion
WHERE alumno.legajo = inscripcion.legajo
GROUP BY nombre

-- 2.9
SELECT nombre, COUNT(DISTINCT codigo)
FROM alumno, inscripcion
WHERE alumno.legajo = inscripcion.legajo AND carrera = 'electronica'  
GROUP BY nombre

-- 2.10
SELECT carrera, COUNT(*)
FROM alumno
GROUP BY carrera

-- 2.11
SELECT carrera, COUNT(*)
FROM alumno
GROUP BY carrera
HAVING COUNT(*) >= ALL (
    SELECT COUNT(*)
    FROM alumno
    GROUP BY carrera
)

-- 2.12


-- 2.13
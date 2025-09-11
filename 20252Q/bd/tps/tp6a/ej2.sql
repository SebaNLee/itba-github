


-- 2.1
SELECT nombre
FROM alumno
WHERE legajo IN (SELECT legajo FROM inscripcion WHERE anio = 2014);


-- 2.2
SELECT DISTINCT i1.legajo
FROM inscripcion AS i1, inscripcion AS i2
WHERE i1.legajo = i2.legajo AND i1.codigo != i2.codigo


-- 2.3
SELECT nombre
FROM curso
WHERE codigo NOT IN (
    SELECT codigo
    FROM inscripcion, alumno
    WHERE nombre = 'Patan, Pamela' AND inscripcion.legajo = alumno.legajo
);


-- 2.4
SELECT DISTINCT nombre
FROM alumno
WHERE legajo IN (
    SELECT legajo
    FROM curso, inscripcion
    WHERE curso.nombre LIKE '%Programacion%'
)
ORDER BY nombre DESC

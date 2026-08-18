

-- a)
SELECT id_distribuidor, id_departamento, nombre_departamento
FROM departamento;

-- b)
SELECT apellido, nombre, telefono
FROM empleado
WHERE e_mail LIKE '%@gmail.com' AND sueldo > 1000;

-- c)
SELECT DISTINCT id_tarea
FROM empleado;

-- d)
SELECT nombre, apellido, telefono
FROM empleado
WHERE id_tarea = 'T001'
ORDER BY apellido, nombre ASC;

-- e)
SELECT CONCAT(nombre, ', ', apellido) AS nombre_apellido, DATE_FORMAT(fecha_nacimiento, '%d-%m') AS dia_mes_cumple
FROM empleado
ORDER BY EXTRACT(MONTH FROM fecha_nacimiento), EXTRACT(DAY FROM fecha_nacimiento) ASC;

-- f)
SELECT CONCAT(nombre, ', ', apellido) AS 'Apellido y Nombre', e_mail AS 'Direccion del mail'
FROM empleado
WHERE telefono LIKE '600%';

-- g)
SELECT apellido, id_empleado
FROM empleado
WHERE porc_comision = 0;

-- h)
SELECT id_distribuidor, nombre, direccion
FROM distribuidor
WHERE telefono IS NULL;

-- i)
SELECT idioma, COUNT(*) AS 'cantidad_peliculas'
FROM pelicula
GROUP BY idioma;

-- j)
SELECT id_departamento, id_distribuidor, COUNT(id_empleado) AS 'cant_empleados' 
FROM empleado
GROUP BY id_departamento, id_distribuidor;

-- k)
SELECT codigo_pelicula, COUNT(nro_entrega) AS 'cant_entregas'
FROM renglon_entrega
GROUP BY codigo_pelicula
HAVING nro_entrega >= 3 ADN nro_entrega <= 5;

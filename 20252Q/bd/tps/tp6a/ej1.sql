

-- seteo de formato
SET datastyle TO iso, dmy;


-- 1.1
SELECT codigo, fecha
FROM penalizacion
WHERE fecha >= '1/1/2015' AND fecha < '1/1/2016';


-- 1.2
SELECT DISTINCT nombre
FROM jugador, penalizacion
WHERE jugador.codigo = penalizacion.codigo;

-- 1.3
SELECT nombre
FROM jugador
WHERE codigo NOT IN (SELECT codigo FROM penalizacion);

-- 1.4
SELECT nombre, TO_CHAR(fecha, 'yyy-mm-dd') as fecha
FROM jugador, penalizacion
WHERE jugador.codigo = penalizacion.codigo
UNION
SELECT nombre, '-' as fecha
FROM jugador
WHERE codigo NOT IN (SELECT codigo FROM penalizacion)

-- 1.5
-- es case sensitive

-- 1.6
SELECT codigo, nombre, telefono
FROM jugador
WHERE telefono LIKE '4314%'
ORDER BY codigo DESC






-- 1.1
SELECT *
FROM jugador;

SELECT *
FROM penalizacion;

SELECT
    codigo,
    EXTRACT(DAY FROM fecha),
    EXTRACT(MONTH FROM fecha),
    EXTRACT(YEAR FROM fecha),
    monto
FROM penalizacion;


-- 1.2
SELECT MAX(monto)
FROM penalizacion


-- 1.3
SELECT MAX(monto) AS maximo_14
FROM penalizacion
WHERE EXTRACT(YEAR FROM fecha) = 2014


-- 1.4
SELECT SUM(monto), nombre
FROM jugador, penalizacion
WHERE jugador.codigo = penalizacion.codigo
GROUP BY nombre
ORDER BY nombre ASC


-- 1.5
SELECT SUM(monto / 8.5), nombre
FROM jugador, penalizacion
WHERE jugador.codigo = penalizacion.codigo
GROUP BY nombre
ORDER BY nombre ASC


-- 1.6
SELECT nombre, COALESCE(TO_CHAR(MIN(fecha), 'yyyy-mm-dd'), '-') as fecha
FROM jugador LEFT OUTER JOIN penalizacion ON jugador.codigo = penalizacion.codigo
GROUP BY jugador.codigo


-- 1.7
SELECT nombre, COALESCE(TO_CHAR(MIN(fecha), 'dd-mm-yyyy'), '-') as fecha
FROM jugador LEFT OUTER JOIN penalizacion ON jugador.codigo = penalizacion.codigo
GROUP BY jugador.codigo


-- 1.8
-- ?


-- 1.9 1.10
-- no se puede editar valores de VIEW


-- 1.11
INSERT INTO penalizacion VALUES('R07', '30/11/2014', '200')


-- 1.12
-- salto


-- 1.13
-- salto


-- 1.14
SELECT nombre,
CASE 
    WHEN acum = 0 THEN 'TRANQUILO'
    WHEN acum = 0 THEN 'TRANQUILO'
    WHEN acum = 0 THEN 'TRANQUILO'
    ELSE 'VIOLENTO'
END AS categoria
FROM 

-- ?
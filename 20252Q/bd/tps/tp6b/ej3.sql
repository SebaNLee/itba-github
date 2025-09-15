

-- 3.2
SELECT codEdit
FROM editorial
WHERE codEdit IS NOT NULL
GROUP BY codEdit
HAVING COUNT(*) = 1

-- 3.3
SELECT titulo, autor
FROM libro
WHERE ISBN IN (
    SELECT ISBN
    FROM detalle_factura
    GROUP BY ISBN
    HAVING SUM(cantidad) >= ALL (
        SELECT SUM(cantidad)
        FROM detalle_factura
        GROUP BY ISBN
    )
)

-- 3.4
SELECT factura.codFact, TO_CHAR(fecha, 'DD-MM-YYYY')
FROM factura, detalle_factura
WHERE factura.codFact = detalle_factura.codFactAND AND ISBN IN (
    SELECT ISBN
    FROM libro
    WHERE precio = (SELECT MIN(precio) FROM LIBRO)
)

-- 3.5
SELECT nombre
FROM editorial, libro
WHERE editorial.codEdit = libro.codEdit AND precio = (
    SELECT MAX(precio)
    FROM libro
    WHERE codEdit IS NOT NULL
)

-- 3.6
SELECT nombre, CAST(SUM(precio), DECIMAL(10, 2)) * 0.2
FROM editorial
-- ??

-- 3.7
SELECT codFact
FROM detalle_factura
GROUP BY codFact
HAVING SUM(cantidad) >= ALL (
    SELECT sum(cantidad)
    FROM detalle_factura
    GROUP BY codFact
)

-- 3.8
-- salto

-- 3.9
SELECT DISTINCT titulo
FROM detalle_factura, libro, factura
WHERE
    detalle_factura.ISBN = libro.ISBN AND
    detalle_factura.codFact = factura.codFact AND
    fecha BETWEEN DATE('2015-09-20') AND DATE('2015-09-30')

-- 3.10
-- ??

-- 3.11
-- ??

-- 3.12
SELECT 
    DATE_PART('MONTH', AGE(MAX(fecha), '2015-01-01')) AS MESES,
    DATE_PART('DAY', AGE(MAX(fecha), '2015-01-01')) AS DIAS
FROM factura
WHERE fecha BETWEEN '2015-09-01' AND '2015-09-30'

-- 3.13
-- salto

-- 3.14
BEGIN;
-- código temporal
ROLLBACK;

-- 3.15
-- ??

-- 3.16


-- salto los demás
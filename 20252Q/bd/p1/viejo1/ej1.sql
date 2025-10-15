
-- scripts en _source



-- 1.1
-- lo hago por complement
-- autores que sí compartieron autoría
SELECT DISTINCT a1.autorID
FROM autoria as a1, autoria as a2
WHERE a1.ISBN LIKE a2.ISBN AND a1.autorID != a2.autorID

-- selecciono autores que no estén en lo de arriba
SELECT nombre
FROM autor
WHERE autorID NOT IN (
    SELECT DISTINCT a1.autorID
    FROM autoria as a1, autoria as a2
    WHERE a1.ISBN LIKE a2.ISBN AND a1.autorID != a2.autorID
)


-- 1.2
-- cálculo de porcentajes
SELECT legajo, (1 - COUNT(fechaDevolucion) / COUNT(*)::DECIMAL(10,2)) * 100 as porcentaje -- o con IS NOT NULL
FROM prestamo
GROUP BY legajo

-- filtro porcentajes > 50% y lo formateo
SELECT legajo, porcentaje::TEXT || ' %'
FROM (
    SELECT legajo, (1 - COUNT(fechaDevolucion) / COUNT(*)::DECIMAL(10,2)) * 100 as porcentaje -- o con IS NOT NULL
    FROM prestamo
    GROUP BY legajo
)
WHERE porcentaje > 50


-- 1.3
-- obtengo ejemplares dentro de la biblioteca
SELECT DISTINCT ISBN
FROM prestamo
WHERE fechaDevolucion IS NOT NULL

-- consulta final
SELECT ISBN, MAX(EXTRACT(DAY FROM fechaDevolucion) - EXTRACT(DAY FROM fechaPrestamo)) as PERIODOMAXIMO
FROM prestamo
WHERE ISBN IN (
    SELECT DISTINCT ISBN
    FROM prestamo
    WHERE fechaDevolucion IS NOT NULL
)
GROUP BY ISBN
-- para anular 0s podría wrappear en otra anidación WHERE != 0


-- 1.4
-- cantidad de ejemplares por ISBN
SELECT ISBN, COUNT(subnumero)
FROM ejemplar
GROUP BY ISBN

-- ISBN que cumplen con lo pedido
SELECT ISBN
FROM prestamo as p
GROUP BY ISBN, legajo, subnumero
HAVING COUNT(DISTINCT subnumero) = (
    SELECT COUNT(DISTINCT subnumero)
    FROM ejemplar as e
    WHERE p.ISBN = e.ISBN
)


-- 1.5
-- obtengo ISBN, interval max de demora
SELECT ISBN, MAX(fechaDevolucion - fechaPrestamo) as delay
FROM prestamo
GROUP BY ISBN

-- consulta final
SELECT ISBN
FROM (
    SELECT ISBN, MAX(fechaDevolucion - fechaPrestamo) as delay
    FROM prestamo
    GROUP BY ISBN
)
WHERE EXTRACT(DAYS FROM delay) <= 0
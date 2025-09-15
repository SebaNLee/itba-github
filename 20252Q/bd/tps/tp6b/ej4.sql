
-- 4.2
SELECT nombre
FROM proveedor, pedido
WHERE proveedor.nroProv = pedido.nroProv
GROUP BY proveedor.nroProv
HAVING COUNT(DISTINCT nroCli) >= ALL (
    SELECT COUNT(DISTINCT nroCli)
    FROM pedido
    GROUP BY nroProv
) 

-- 4.3
SELECT DISTINCT nombre
FROM proveedor, pedido
WHERE 
    proveedor.nroProv = pedido.nroProv AND
    NOT EXISTS (
        SELECT *
        FROM pedido AS p1 , producto
        WHERE p1.codProd = producto.codProd
        AND p1.nroProv = pedido.nroProv
        AND producto.genero LIKE 'Ingles'
    )

-- 4.4
SELECT nombre, direccion, ciudad
FROM cliente JOIN pedido ON cliente.nroCli = pedido.nroCli
WHERE fecha = (
    SELECT MIN(fecha)
    FROM pedido
)

-- 4.5
-- salto

-- 4.6
-- ??

-- 4.7
SELECT DISTINCT c1.nombre, c2.nombre, p1.fecha
FROM cliente c1, cliente c2, pedido p1, pedido p2
WHERE
    c1.nroCli = p1.nroCli AND
    c2.nroCli = p2.nroCli AND
    p1.fecha = p2.fecha AND
    c1.nroCli < c2.nroCli

-- 4.8
SELECT nombre, SUM(cantidad) AS cant
FROM cliente JOIN pedido ON cliente.nroCli = pedido.nroCli
GROUP BY cliente.nroCli
HAVING SUM(cantidad) > (
    SELECT AVG(cantidad)
    -- ??
)





-- salto siguientes
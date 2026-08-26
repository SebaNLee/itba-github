
-- a)
DROP VIEW IF EXISTS ENVIOS500;

CREATE VIEW ENVIOS500 AS
SELECT id_proveedor, id_articulo, cantidad
FROM ENVIO
WHERE cantidad >= 500;

SELECT * FROM ENVIOS500;
-- sí es modificable pues: conserva columnas PK, sin funciones de agregación ni DISTINCT ni subconsultas SELECT 

-- b)
DROP VIEW IF EXISTS ENVIOS500_999;

CREATE VIEW ENVIOS500_999 AS
SELECT *
FROM ENVIOS500
WHERE cantidad >= 500 AND cantidad <= 999; -- medio al pedo el >= 500

SELECT * FROM ENVIOS500_999;
-- sí es modificable: ídem y se crea a partir de VIEW modificable

-- c)
DROP VIEW IF EXISTS detalle_envios;

CREATE VIEW detalle_envios AS
SELECT descrip, peso, nombre, cantidad
FROM ENVIOS500 e
JOIN PROVEEDOR p ON p.id_proveedor = e.id_proveedor
JOIN ARTICULO a ON a.id_articulo = e.id_articulo; 

SELECT * FROM detalle_envios;
-- no es actualizable, no tiene columnas PK


-- i) - iv)

-- Básicamente no deja modificar con WCO si viola condición del VIEW, sino deja y desaparece del VIEW
INSERT INTO ENVIOS500 VALUES ('PI', 'Al', 500);
INSERT INTO ENVIOS500 VALUES ('P2', 'A2', 300);
UPDATE ENVIOS500 SET cantidad=1000 WHERE id proveedor='Pl';
UPDATE ENVIOS500 SET cantidad=100 WHERE id proveedor='Pl';
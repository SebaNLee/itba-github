
DROP VIEW buenos_proveedores IF EXISTS;

CREATE VIEW buenos_proveedores (prov, rub, ciudad ) AS
SELECT id proveedor, rubro, ciudad
FROM PROVEEDOR
WHERE rubro IN ('Alimentos', 'Agro', 'Salud', 'Farmacia')
WITH CHECK OPTION;


-- a)
INSERT INTO buenos proveedores (prov, rub, ciudad) VALUES ('10', 'Farmacia', 'Paris');

-- b) Volver a ejecutar el INSERT anterior modificando previamente el campo NOMBRE de la tabla PROVEEDOR para que permita nulos
-- dejaría (porque ahora permite NULL, PK intacta, y cumple condición WCO)

-- c)
-- UPDATE buenos proveedores SET rub = 'Educacion' WHERE prov = '10';
-- no deja, pues no respeta WCO

-- d)
INSERT INTO buenos proveedores (prov, rub, ciudad) VALUES ('8', 'Deportes', 'Roma');
-- no deja, idem WCO

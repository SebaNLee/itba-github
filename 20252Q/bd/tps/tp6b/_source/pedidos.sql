
-- datos, de un Drive




CREATE TABLE proveedor(
        nroProv INTEGER NOT NULL,
        nombre TEXT,
        ciudad TEXT,
        PRIMARY KEY(nroProv)
); 

CREATE TABLE producto(
        codProd INTEGER NOT NULL,
        descripcion TEXT,
        PRIMARY KEY(codProd)
);     

CREATE TABLE cliente(
        nroCli INTEGER NOT NULL,
        nombre TEXT,
        ciudad TEXT,
        PRIMARY KEY(nroCli)
);

CREATE TABLE pedido(
        nroCli INTEGER NOT NULL,
        nroProv INTEGER NOT NULL,
        codProd INTEGER NOT NULL,
        cantidad INTEGER,
        fecha DATE NOT NULL,
        PRIMARY KEY(nroCli, nroProv, codProd, fecha),
        FOREIGN KEY(nroCli) REFERENCES cliente ON DELETE CASCADE ON UPDATE RESTRICT,
        FOREIGN KEY(nroProv) REFERENCES proveedor ON DELETE CASCADE ON UPDATE RESTRICT,
        FOREIGN KEY(codProd) REFERENCES producto ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO proveedor VALUES(10,'Importaciones ABC','Capital Federal');
INSERT INTO proveedor VALUES(20,'Revistas Unidas','Rosario');
INSERT INTO proveedor VALUES(30,'Distribuidora Manfretti','Cordoba');
INSERT INTO proveedor VALUES(40,'Distribuidora PPP','Rosario');
INSERT INTO producto VALUES(1,'Revista Gente');
INSERT INTO producto VALUES(2,'Caras');
INSERT INTO producto VALUES(3,'Foco');
INSERT INTO producto VALUES(4,'Speak Up');
INSERT INTO producto VALUES(5,'Learn English');
INSERT INTO producto VALUES(6,'El Grafico');
INSERT INTO producto VALUES(7,'Pelo');
INSERT INTO producto VALUES(8,'100 recetas fáciles-Arguiñano');
INSERT INTO producto VALUES(9,'Cocinando con Choly Berreteaga');
INSERT INTO producto VALUES(10,'La Nación');
INSERT INTO producto VALUES(11,'Pagina 12');
INSERT INTO producto VALUES(12,'Clarín');
INSERT INTO producto VALUES(13,'La Razon');
INSERT INTO producto VALUES(14,'Buenos Aires Herald');

INSERT INTO cliente VALUES(10,'Felipe Garcia','Capital Federal');
INSERT INTO cliente VALUES(20,'Anastasia Le Court','Capital Federal');
INSERT INTO cliente VALUES(30,'Teodora Boorman','Rosario');
INSERT INTO cliente VALUES(40,'Jose Ryan','Rosario');
INSERT INTO cliente VALUES(50,'Maria Carbajal','Capital Federal');
INSERT INTO cliente VALUES(60,'Sonia Santillan','Capital Federal');
INSERT INTO cliente VALUES(70,'ITBA','Capital Federal');
INSERT INTO cliente VALUES(80,'Franco Ronelli','Rosario');
INSERT INTO cliente VALUES(90,'Monica Musso','Parana');

SET datestyle TO dmy;
INSERT INTO pedido VALUES(10,10,4,1,'30/06/15');
INSERT INTO pedido VALUES(10,10,5,3,'30/06/15');
INSERT INTO pedido VALUES(40,30,9,1,'15/09/15');
INSERT INTO pedido VALUES(40,10,11,4,'10/12/14');
INSERT INTO pedido VALUES(40,10,1,1,'12/10/13');
INSERT INTO pedido VALUES(50,30,10,1,'08/04/14');
INSERT INTO pedido VALUES(50,40,11,1,'30/10/14');
INSERT INTO pedido VALUES(50,30,12,1,'31/01/14');
INSERT INTO pedido VALUES(50,30,13,1,'08/04/14');
INSERT INTO pedido VALUES(50,40,14,1,'31/01/14');
INSERT INTO pedido VALUES(70,40,10,3,'29/02/12');
INSERT INTO pedido VALUES(70,40,11,3,'29/02/12');
INSERT INTO pedido VALUES(70,40,12,1,'29/02/12');
INSERT INTO pedido VALUES(70,30,12,2,'18/02/12');
INSERT INTO pedido VALUES(70,40,13,3,'29/02/12');
INSERT INTO pedido VALUES(70,40,14,3,'29/02/12');
INSERT INTO pedido VALUES(90,40,2,2,'08/09/15');
INSERT INTO pedido VALUES(90,30,2,3,'10/02/13');
INSERT INTO pedido VALUES(90,30,8,1,'30/06/15');

ALTER TABLE producto ADD genero TEXT;
ALTER TABLE cliente ADD direccion TEXT;

UPDATE producto SET genero = 'Chismes' WHERE codProd IN (1, 2, 3);
UPDATE producto SET genero = 'Ingles' WHERE codProd IN (4, 5);
UPDATE producto SET genero = 'Deporte' WHERE codProd = 6;
UPDATE producto SET genero = 'Musical' WHERE codProd = 7;
UPDATE producto SET genero = 'Cocina' WHERE codProd IN (8, 9);
UPDATE producto SET genero = 'Diario' WHERE codProd IN (10, 11, 12, 13, 14);

UPDATE cliente SET direccion = 'Tucuman 110' WHERE nroCli = 10;
UPDATE cliente SET direccion = 'Alvear 1256' WHERE nroCli = 20;
UPDATE cliente SET direccion = '25 de Mayo 1400' WHERE nroCli = 30;
UPDATE cliente SET direccion = '25 de Mayo 240' WHERE nroCli = 40;
UPDATE cliente SET direccion = 'Viamonte 6822' WHERE nroCli = 50;
UPDATE cliente SET direccion = 'Alem 500' WHERE nroCli = 60;
UPDATE cliente SET direccion = 'Madero 399' WHERE nroCli = 70;
UPDATE cliente SET direccion = 'Francia 200' WHERE nroCli = 80;
UPDATE cliente SET direccion = 'Lima 189' WHERE nroCli = 90;
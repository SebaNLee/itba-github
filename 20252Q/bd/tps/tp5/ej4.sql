

CREATE TABLE proveedor
(
    nroProv INTEGER NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    ciudad VARCHAR(30),

    PRIMARY KEY(nroProv)
);

CREATE TABLE producto
(
    codProd INTEGER NOT NULL,
    descripcion VARCHAR(30) NOT NULL,

    PRIMARY KEY(codProd)
);

CREATE TABLE cliente
(
    nroCli INTEGER NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    ciudad VARCHAR(30) NOT NULL,

    PRIMARY KEY(nroCli)
);

CREATE TABLE pedido
(
    nroCli INTEGER NOT NULL,
    nroProv INTEGER NOT NULL,
    codProd INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    fecha DATE NOT NULL,

    PRIMARY KEY(nroCli, nroProv, codProd, fecha), -- !! todas conforman el PK
    FOREIGN KEY(nroCli) REFERENCES cliente ON DELETE CASCADE,
    FOREIGN KEY(nroProv) REFERENCES proveedor ON DELETE CASCADE,
    FOREIGN KEY(codProd) REFERENCES producto ON DELETE CASCADE
);


-- inserts

INSERT INTO proveedor VALUES(10, 'Importaciones ABC', 'Capital Federal');
INSERT INTO producto VALUES(4, 'Speak Up');
INSERT INTO cliente VALUES(10, 'Felipe Garcia', 'Capital Federal');

SET datestyle TO dmy; -- !! formato de fecha para inserción 
INSERT INTO pedido VALUES(10, 10, 4, 1, '30/06/15');

SELECT * FROM pedido;


-- modificaciones 

ALTER TABLE producto ADD genero VARCHAR(30);
ALTER TABLE cliente ADD direccion VARCHAR(30);


-- nuevos inserts
INSERT INTO cliente VALUES(20, 'Anastasia Le Court', 'Capital Federal', 'Tucuman 110');
INSERT INTO pedido VALUES(20, 10, 4, 1, '23/07/15');

SELECT * FROM pedido;
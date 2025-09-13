





-- 3.1
SELECT titulo, COALESCE(autor, '-'), precio
FROM libro
WHERE genero LIKE 'Infantil'


-- 3.2
SELECT titulo, nombre
FROM libro, editorial
WHERE precio < 27.5 AND libro.codEdit = editorial.codEdit


-- 3.3
SELECT titulo
FROM libro
ORDER BY titulo ASC


-- 3.4
SELECT titulo, nombre
FROM libro, editorial
WHERE libro.codEdit = editorial.codEdit AND pais LIKE 'Canada'


-- 3.5

-- son monónotos, salteo













-- datos

CREATE TABLE editorial(
        codEdit CHAR(3) NOT NULL,
        direccion VARCHAR(30),
        nombre VARCHAR(30),
        pais VARCHAR(20),
        PRIMARY KEY(codEdit)
);

CREATE TABLE libro (
        ISBN VARCHAR(8) NOT NULL,
        titulo VARCHAR(30),
        autor VARCHAR(30),
        codEdit CHAR(3),
        genero VARCHAR(20),
        precio FLOAT,
        PRIMARY KEY(ISBN),
        FOREIGN KEY(codEdit) REFERENCES editorial ON DELETE SET NULL ON UPDATE RESTRICT
);

CREATE TABLE factura(
        codFact INTEGER NOT NULL,
        fecha DATE,
        monto FLOAT,
        PRIMARY KEY(codFact)
);

CREATE TABLE detalle_factura(
        codFact INT NOT NULL,
        nroLinea INT NOT NULL,
        ISBN VARCHAR(8) NOT NULL,
        cantidad INT,
        PRIMARY KEY(codFact, nroLinea),
        FOREIGN KEY(codFact) REFERENCES factura ON DELETE CASCADE ON UPDATE RESTRICT,
        FOREIGN KEY(ISBN) REFERENCES libro ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO editorial VALUES('WO5', NULL, 'Addison Wesley', 'Canada');
INSERT INTO editorial VALUES('XA3', NULL, 'Prentice Hall', 'USA');
INSERT INTO editorial VALUES('BO3', NULL, 'Mc Graw Hill', 'USA');

INSERT INTO libro VALUES('15030108', 'El lenguaje c', 'Kernighan', 'XA3', 'Computacion', 28.50);
INSERT INTO libro VALUES('16015229', 'Perlas de prog.', 'Bentley', 'WO5', 'Computacion', 118.00);
INSERT INTO libro VALUES('30111230', 'Blancanieves', NULL, 'XA3', 'Infantil', 32.30);
INSERT INTO libro VALUES('18000111', 'Alicia en el pais', 'Carroll', 'BO3', 'Infantil', 52.00);
INSERT INTO libro VALUES('15030109', 'Origami', 'Youtang', 'XA3', 'Manualidades', 20.00);
INSERT INTO libro VALUES('16000205', 'Unix', 'Steavens', 'WO5', 'Computacion', 83.50);
INSERT INTO libro VALUES('18010300', 'El bigote', 'Carriere', 'WO5', 'Novela', 53.90);
INSERT INTO libro VALUES('17001030', 'Sr presidente', 'Asturias', NULL, 'Novela', 120.00);

SET datestyle to DMY;
INSERT INTO factura VALUES(1000, '03/09/2015', 46.50);
INSERT INTO factura VALUES(1001, '03/09/2015', 124.30);
INSERT INTO factura VALUES(1002, '05/09/2015', 130);
INSERT INTO factura VALUES(1003, '30/09/2015 ', 157.90);
INSERT INTO factura VALUES(1004, '09/09/2015', 38); 

INSERT INTO detalle_factura VALUES(1000,1,'15030108',1);
INSERT INTO detalle_factura VALUES(1000,2,'16015229',1);
INSERT INTO detalle_factura VALUES(1001,1,'17001030',1);
INSERT INTO detalle_factura VALUES(1001,2,'30111230',1);
INSERT INTO detalle_factura VALUES(1001,3,'15030109',2);
INSERT INTO detalle_factura VALUES(1002,1,'16015229',1);
INSERT INTO detalle_factura VALUES(1002,2,'16000205',1);
INSERT INTO detalle_factura VALUES(1002,3,'15030108',1);
INSERT INTO detalle_factura VALUES(1003,1,'18010300',1);
INSERT INTO detalle_factura VALUES(1003,2,'18000111',2);
INSERT INTO detalle_factura VALUES(1004,1,'15030109',1);
INSERT INTO detalle_factura VALUES(1004,2,'16015229',1);

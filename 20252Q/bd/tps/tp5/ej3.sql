CREATE TABLE editorial
(
    codEdit VARCHAR(20) NOT NULL,
    direccion VARCHAR(20),
    nombre VARCHAR(20),
    pais VARCHAR(20),

    PRIMARY KEY(codEdit)
);

CREATE TABLE libro
(
    ISBN VARCHAR(20) NOT NULL,
    titulo VARCHAR(20),
    autor VARCHAR(20),
    codEdit VARCHAR(20),
    genero VARCHAR(20),
    precio FLOAT,

    PRIMARY KEY(ISBN),
    FOREIGN KEY(codEdit) REFERENCES editorial ON DELETE SET NULL ON UPDATE RESTRICT
);

CREATE TABLE factura
(
    codFact INTEGER NOT NULL,
    fecha DATE,
    monto FLOAT,
    
    PRIMARY KEY(codFact)
);

CREATE TABLE detalle_factura
(
    codFact INTEGER NOT NULL,
    nroLinea INTEGER NOT NULL,
    ISBN VARCHAR(8) NOT NULL,
    cantidad INTEGER,

    PRIMARY KEY(nroLinea),
    FOREIGN KEY(codFact) REFERENCES factura ON DELETE CASCADE ON UPDATE RESTRICT,
    FOREIGN KEY(ISBN) REFERENCES libro ON DELETE CASCADE ON UPDATE RESTRICT
);


-- inserts

INSERT INTO editorial VALUES('XA3', null, 'Addison Wesley', 'Canada');
INSERT INTO libro VALUES(15030108, 'El lenguaje C', 'Kernighan', 'XA3', 'Computacion', 28.50);


-- verif

SELECT * FROM libro;
SELECT * FROM editorial;
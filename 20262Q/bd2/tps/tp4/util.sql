-- copiado de apuntes Nash, y lo adapto

DROP TABLE IF EXISTS ENVIO;
DROP TABLE IF EXISTS ARTICULO;
DROP TABLE IF EXISTS PROVEEDOR;

CREATE TABLE PROVEEDOR (
    id_proveedor VARCHAR(10) NOT NULL,
    nombre       VARCHAR(40) NOT NULL,
    rubro        VARCHAR(20) NOT NULL,
    ciudad       VARCHAR(40) NOT NULL,
    CONSTRAINT pk_proveedor PRIMARY KEY (id_proveedor)
);

CREATE TABLE ARTICULO (
    id_articulo VARCHAR(10) NOT NULL,
    descrip     VARCHAR(60) NOT NULL,
    peso        DECIMAL(6,2) NOT NULL,
    ciudad      VARCHAR(40) NOT NULL,
    CONSTRAINT pk_articulo PRIMARY KEY (id_articulo)
);

CREATE TABLE ENVIO (
    id_proveedor VARCHAR(10) NOT NULL,
    id_articulo  VARCHAR(10) NOT NULL,
    cantidad     INT NOT NULL,
    CONSTRAINT pk_envio PRIMARY KEY (id_proveedor, id_articulo),
    CONSTRAINT fk_envio_proveedor FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR(id_proveedor),
    CONSTRAINT fk_envio_articulo  FOREIGN KEY (id_articulo)  REFERENCES ARTICULO(id_articulo)
);


INSERT INTO PROVEEDOR (id_proveedor, nombre, rubro, ciudad) VALUES
('P001', 'Proveedor Sanos',      'Alimentos', 'Madrid'),
('P002', 'Campo Verde S.A.',     'Agro',      'Lisboa'),
('P003', 'Vida Plena',           'Salud',     'Barcelona'),
('P004', 'Farmacias Unidas',     'Farmacia',  'Paris'),
('P005', 'Super Agro Coop',      'Agro',      'Roma'),
('P006', 'NutriFoods',           'Alimentos', 'Valencia'),
('P007', 'Salud Global',         'Salud',     'Sevilla');

INSERT INTO ARTICULO (id_articulo, descrip, peso, ciudad) VALUES
('A001', 'Televisor 42 pulgadas', 12.50, 'Madrid'),
('A002', 'Camiseta algodón',       0.25,  'Barcelona'),
('A003', 'Caja de galletas',       1.20,  'Valencia'),
('A004', 'Detergente 5L',          5.00,  'Sevilla');

INSERT INTO ENVIO (id_proveedor, id_articulo, cantidad) VALUES
('P001', 'A001', 300),   -- < 500
('P001', 'A002', 800),   -- 500..999
('P002', 'A003', 1200),  -- >= 1000
('P003', 'A004', 600),   -- 500..999
('P004', 'A001', 520);   -- 500..999

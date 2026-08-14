DROP TABLE IF EXISTS compone;
DROP TABLE IF EXISTS producto_quimico;
DROP TABLE IF EXISTS pq_liquido;
DROP TABLE IF EXISTS pq_solido;
DROP TABLE IF EXISTS envio;
DROP TABLE IF EXISTS cliente;

CREATE TABLE producto_quimico (
    id_prod_quim INT NOT NULL,
    nombre_prod_quim VARCHAR(20) NOT NULL,
    formula VARCHAR(20) NOT NULL,
    tipo_pq VARCHAR(20) NOT NULL,

    CONSTRAINT PRIMARY KEY (id_prod_quim) 
);

CREATE TABLE pq_liquido (
    id_prod_quim INT NOT NULL,
    inflamable VARCHAR(20) NOT NULL,
    tipo_envase VARCHAR(20) NOT NULL,
    cond_traslado VARCHAR(20) NOT NULL,

    CONSTRAINT PRIMARY KEY (id_prod_quim),
    CONSTRAINT FOREIGN KEY (id_prod_quim) REFERENCES producto_quimico
);

CREATE TABLE pq_solido (
    id_prod_quim INT NOT NULL,
    forma VARCHAR(20) NOT NULL,
    empaque_max INT NOT NULL,

    CONSTRAINT PRIMARY KEY (id_prod_quim),
    CONSTRAINT FOREIGN KEY (id_prod_quim) REFERENCES producto_quimico
);

CREATE TABLE cliente (
    id_cliente INT NOT NULL,
    CUIT INT UNIQUE NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    -- bla bla ba
    es_garante INT, -- !! ojo este es nullable

    CONSTRAINT PRIMARY KEY (id_cliente),
    CONSTRAINT FOREIGN KEY (es_garante) REFERENCES cliente
);

CREATE TABLE envio (
    nro_envio INT NOT NULL,
    cantidad INT NOT NULL,
    peso FLOAT NOT NULL,
    id_prod_quim INT NOT NULL,
    id_cliente INT NOT NULL,

    CONSTRAINT PRIMARY KEY (nro_envio),
    CONSTRAINT FOREIGN KEY (id_prod_quim) REFERENCES producto_quimico,
    CONSTRAINT FOREIGN KEY (id_cliente) REFERENCES cliente
);

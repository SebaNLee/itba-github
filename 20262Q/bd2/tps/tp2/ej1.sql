DROP TABLE IF EXISTS contiene;
DROP TABLE IF EXISTS palabra;
DROP TABLE IF EXISTS articulo;

CREATE TABLE palabra (
    cod_p INT NOT NULL,
    idioma CHAR(2) NOT NULL,
    descrip VARCHAR(25) NOT NULL,

    CONSTRAINT PRIMARY KEY (cod_p, idioma)
);

CREATE TABLE articulo (
    id_articulo INT NOT NULL,
    titulo VARCHAR(120) UNIQUE NOT NULL,
    autor VARCHAR(30) NOT NULL,
    fecha_pub FLOAT NOT NULL,
    nacional CHAR (10) NOT NULL,

    CONSTRAINT PRIMARY KEY (id_articulo)
);

CREATE TABLE contiene (
    cod_p INT NOT NULL,
    idioma CHAR(2) NOT NULL,
    id_articulo INT NOT NULL,

    CONSTRAINT FOREIGN KEY (cod_p, idioma) REFERENCES palabra,
    CONSTRAINT FOREIGN KEY (id_articulo) REFERENCES articulo,
    CONSTRAINT PRIMARY KEY (cod_p, idioma, id_articulo)
);

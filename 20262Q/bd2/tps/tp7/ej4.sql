
DROP TABLE IF EXISTS ARTICULO;
DROP TABLE IF EXISTS PALABRA;
DROP TABLE IF EXISTS CONTIENE;
DROP TABLE IF EXISTS TEXTOSPORAUTOR;


-- tablas de consigna
CREATE TABLE ARTICULO (
    id_articulo   INT PRIMARY KEY,
    titulo        VARCHAR(200) NOT NULL,
    autor         VARCHAR(150) NOT NULL,
    nacionalidad  VARCHAR(50),
    fecha_pub     DATE NOT NULL
);

CREATE TABLE PALABRA (
    idioma       VARCHAR(20) NOT NULL,
    cod_palabra  INT NOT NULL,
    descripcion  VARCHAR(200),
    PRIMARY KEY (idioma, cod_palabra)
);

CREATE TABLE CONTIENE (
    id_articulo  INT NOT NULL,
    idioma       VARCHAR(20) NOT NULL,
    cod_palabra  INT NOT NULL,
    nro_seccion  INT NOT NULL,
    PRIMARY KEY (id_articulo, idioma, cod_palabra),
    FOREIGN KEY (id_articulo) REFERENCES ARTICULO(id_articulo),
    FOREIGN KEY (idioma, cod_palabra) REFERENCES PALABRA(idioma, cod_palabra)
);

-- tabla trigger
CREATE TABLE TEXTOSPORAUTOR (
  autor                VARCHAR(200) PRIMARY KEY,
  cant_textos          INT NOT NULL,
  fecha_ultima_public  DATE NOT NULL
);

INSERT INTO TEXTOSPORAUTOR (autor, cant_textos, fecha_ultima_public)
SELECT autor, COUNT(*) AS cant_textos, MAX(fecha_pub) AS fecha_ultima_public
FROM ARTICULO
GROUP BY autor;

-- i)
DROP TRIGGER IF EXISTS articulo_after_insert;

DELIMITER //
CREATE TRIGGER articulo_after_insert
AFTER INSERT ON ARTICULO
FOR EACH ROW
BEGIN
    -- https://dev.mysql.com/doc/refman/8.4/en/insert-on-duplicate.html
    INSERT INTO TEXTOSPORAUTOR
    VALUES (NEW.autor, 1, NEW.fecha_pub)
    ON DUPLICATE KEY UPDATE
        cant_textos = cant_textos + 1, fecha_ultima_public = GREATEST(fecha_ultima_public, NEW.fecha_pub)
END //
DELIMITER ;


-- ii)

-- iii)
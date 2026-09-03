

-- a)
DROP TABLE IF EXISTS HIS_ENTREGA;

CREATE TABLE HIS_ENTREGA (
    id_log INT NOT NULL AUTO_INCREMENT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operacion ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    usuario VARCHAR(100) NOT NULL,

    PRIMARY KEY (id_log)
);

-- b)
DROP TRIGGER IF EXISTS his_entrega_after_insert;

DELIMITER //
CREATE TRIGGER his_entrega_after_insert
AFTER INSERT ON entrega
FOR EACH ROW
BEGIN
    INSERT INTO HIS_ENTREGA (operacion, usuario)
    VALUES ('INSERT', CURRENT_USER());
END //
DELIMITER ;

-- y así con UPDATE y DELETE...

-- test
DELETE FROM entrega WHERE nro_entrega = 22;
INSERT INTO entrega VALUES (22, CURRENT_TIMESTAMP, 1, 1);
SELECT * FROM HIS_ENTREGA;


-- c)
-- con FOR EACH STATEMENT sería log por operación y no por cada row insertada
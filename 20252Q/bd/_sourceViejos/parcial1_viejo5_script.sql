SET datestyle to DMY;

CREATE TABLE producto (
        prodID int,
        descripcion text,
        PRIMARY KEY(prodID)
        );
CREATE TABLE cliente (
       clientID int,
       nombre text,
       PRIMARY KEY(clientID)
       );
CREATE TABLE encuestador(
       encID int,
       nombre text,
       PRIMARY KEY(encID)
       );
CREATE TABLE log(
       prodID int,
       clientID int,
       encID int,
       fecha date,
       satisfaccion int,
       PRIMARY KEY(prodID,encID,clientID),
       FOREIGN KEY(prodID) references producto,
       FOREIGN KEY(clientID) references cliente,
       FOREIGN KEY(encID) references encuestador
       );
       
INSERT INTO producto values(10,'PYT');       
INSERT INTO producto values(20,'BUH');
INSERT INTO producto values(30,'CRE');
INSERT INTO producto values(40,'GIR');
INSERT INTO producto values(50,'TTO');
INSERT INTO producto values(60,'ABC');

INSERT INTO cliente values(921,'Rial');
INSERT INTO cliente values(1001,'Ray');
INSERT INTO cliente values(3420,'Stewart');
INSERT INTO cliente values(200,'Shrek');
INSERT INTO cliente values(933,'Felicitas');
INSERT INTO cliente values(971,'Dumas');

INSERT INTO encuestador values(102,'Andrade');
INSERT INTO encuestador values(165,'Hedn');
INSERT INTO encuestador values(220,'IANNYS');
INSERT INTO encuestador values(370,'Loprano');
           
INSERT INTO log values(10,921,102,'12-10-2004',5);
INSERT INTO log values(30,921,102,'12-10-2004',5);
INSERT INTO log values(50,921,165,'16-02-2004',5);
INSERT INTO log values(10,3420,165,'16-02-2004',3);
INSERT INTO log values(20,3420,165,'18-02-2004',7);
INSERT INTO log values(30,3420,165,'18-02-2004',7);
INSERT INTO log values(40,3420,220,'12-10-2004',9);
INSERT INTO log values(50,3420,220,'12-10-2004',9);
INSERT INTO log values(10,200,102,'15-02-2004',2);
INSERT INTO log values(30,200,102,'15-02-2004',NULL);
INSERT INTO log values(50,933,220,'15-02-2004',8);
INSERT INTO log values(60,971,370,'15-02-2004',7);
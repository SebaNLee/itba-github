create table libroo4f (
        ISBN text,
        descrip text,
        primary key(ISBN)
       )
;

insert into libroo4f VALUES('007213495X', 'XML Handbook');
insert into libroo4f VALUES('8448130340', 'Manual Oracle');
insert into libroo4f VALUES('0072229527', 'Oracle 10G');
insert into libroo4f VALUES('0471701467', 'Thinking Recursively');
insert into libroo4f VALUES('0201545411', 'Abstractions');
insert into libroo4f VALUES('0764568744', 'Java 1.5');
insert into libroo4f VALUES('0672318288', 'DB2');       


create table autor (
        autorID int,
        nombre text,
        primary key(autorID)
       )
;

insert into autor VALUES(01, 'Chang');
insert into autor VALUES(02, 'Scardina');
insert into autor VALUES(03, 'Kiritzov');
insert into autor VALUES(04, 'Wang');
insert into autor VALUES(05, 'Roberts');
insert into autor VALUES(06, 'Horton');
insert into autor VALUES(07, 'Mullins');

create table autoria (
        ISBN text,
        autorID int,
        primary key(ISBN,autorID),
        foreign key(ISBN) references libroo4f (ISBN),
        foreign key(autorID) references autor (autorID)
       )
;

insert into autoria VALUES('007213495X', 01);
insert into autoria VALUES('007213495X', 02);
insert into autoria VALUES('007213495X', 03);
insert into autoria VALUES('8448130340', 01);
insert into autoria VALUES('0072229527', 03);
insert into autoria VALUES('0072229527', 01);
insert into autoria VALUES('0072229527', 04);
insert into autoria VALUES('0471701467', 05);
insert into autoria VALUES('0201545411', 05);
insert into autoria VALUES('0764568744', 06);
insert into autoria VALUES('0672318288', 07);


create table ejemplar (
        ISBN text,
        subnumero int,
        primary key(ISBN,subnumero),
        foreign key(ISBN) references libroo4f (ISBN)
        )
;

insert into ejemplar VALUES('007213495X',03);
insert into ejemplar VALUES('8448130340',01);
insert into ejemplar VALUES('8448130340',04);
insert into ejemplar VALUES('8448130340',05);
insert into ejemplar VALUES('8448130340',06);
insert into ejemplar VALUES('0471701467',01);
insert into ejemplar VALUES('0471701467',02);
insert into ejemplar VALUES('0471701467',03);
insert into ejemplar VALUES('0201545411',01);
insert into ejemplar VALUES('0764568744',01);
insert into ejemplar VALUES('0764568744',02);
insert into ejemplar VALUES('0764568744',03);
insert into ejemplar VALUES('0672318288',02);
insert into ejemplar VALUES('0672318288',04);

create table prestamo (
        legajo text,
        ISBN text,
        subnumero int,
        fechaPrestamo timestamp,
        fechaDevolucion timestamp,
        primary key(ISBN,subnumero,fechaPrestamo),
        foreign key(ISBN,subnumero) references ejemplar (ISBN,subnumero)
        )
;

insert into prestamo VALUES(111,'007213495X',3,to_timestamp('2005-04-16 08:00','YYYY-MM-DD HH24:MI'),to_timestamp('2005-04-16 11:00','YYYY-MM-DD HH:MI'));
insert into prestamo VALUES(111,'007213495X',3,to_timestamp('2005-04-16 15:00','YYYY-MM-DD hh24:MI'),Null);
insert into prestamo VALUES(222,'8448130340',1,to_timestamp('2005-04-16 08:30','YYYY-MM-DD hh24:MI'),to_timestamp('2005-04-16 09:00','YYYY-MM-DD hh24:MI'));
insert into prestamo VALUES(111,'8448130340',1,to_timestamp('2005-04-16 10:00','YYYY-MM-DD hh24:MI'),Null);
insert into prestamo VALUES(222,'8448130340',5,to_timestamp('2005-04-16 15:00','YYYY-MM-DD hh24:MI'),Null);
insert into prestamo VALUES(333,'8448130340',6,to_timestamp('2005-04-20 15:01','YYYY-MM-DD hh24:MI'),Null);
insert into prestamo VALUES(333,'0471701467',1,to_timestamp('2005-04-20 15:04','YYYY-MM-DD hh24:MI'),Null);
insert into prestamo VALUES(444,'0764568744',1,to_timestamp('2005-04-20 12:04','YYYY-MM-DD hh24:MI'),to_timestamp('2005-04-27 14:04','YYYY-MM-DD hh24:MI'));
insert into prestamo VALUES(222,'0764568744',3,to_timestamp('2005-04-20 11:00','YYYY-MM-DD hh24:MI'),to_timestamp('2005-04-20 15:00','YYYY-MM-DD hh24:MI'));
insert into prestamo VALUES(222,'0764568744',1,to_timestamp('2005-05-21 10:00','YYYY-MM-DD hh24:MI'),to_timestamp('2005-05-29 15:00','YYYY-MM-DD hh24:MI'));
insert into prestamo VALUES(555,'0672318288',2,to_timestamp('2005-02-01 12:00','YYYY-MM-DD hh24:MI'),to_timestamp('2005-02-02 12:00','YYYY-MM-DD hh24:MI'));
insert into prestamo VALUES(555,'0672318288',4,to_timestamp('2005-02-25 12:00','YYYY-MM-DD hh24:MI'),to_timestamp('2005-02-28 10:00','YYYY-MM-DD hh24:MI'));


insert into prestamo VALUES(333,'0471701467',1,'2005-04-20 15:04',Null);
CREATE TABLE cliente(
        dni int,
        nombre text NOT NULL,
        ciudad text NOT NULL,
        PRIMARY KEY (dni)
);

insert into cliente values(44, 'Anabella Luzuriaga', 'USHUAIA');
insert into cliente values(32, 'Manuel Ruiz', 'CABA');
insert into cliente values(27, 'Santiago López', 'LA PLATA');
insert into cliente values(35, 'Luciana Martínez', 'ROSARIO');
insert into cliente values(76, 'José Pelman', 'ROSARIO');
insert into cliente values(12, 'Greta Báez', 'USHUAIA');

CREATE TABLE auto(
        patente text,
        marca text not null,
        modelo text not null,
        color text not null,
        PRIMARY KEY (patente)
);

insert into auto values('fyi654', 'Chevrolet', 'Corsa', 'gris');
insert into auto values('omg987', 'Ford', 'Fiesta', 'azul');
insert into auto values('vip195', 'VW', 'Vento', 'gris');
insert into auto values('aka654', 'VW', 'Suran', 'beige');
insert into auto values('lol123', 'Honda', 'Civic', 'beige');
insert into auto values('mib111', 'Honda', 'Civic', 'azul');

CREATE TABLE alquila(
        dni int,
        patente text,
        fechaR date not null,
        ciudadR text not null,
        fechaD date,
        ciudadD text,
        km int,
        PRIMARY KEY(dni, patente, fechaR),
        FOREIGN KEY (dni) REFERENCES cliente(dni),
        FOREIGN KEY (patente) REFERENCES auto(patente)      
);

insert into alquila values(44, 'vip195', to_date('15/03/2015','DD/MM/YYYY'), 'BARILOCHE');
insert into alquila values(35, 'fyi654', to_date('10/03/2015','DD/MM/YYYY'), 'CABA', to_date('11/03/2015','DD/MM/YYYY'), 'ROSARIO', '660');
insert into alquila values(32, 'fyi654', to_date('14/01/2015','DD/MM/YYYY'), 'CABA', to_date('18/01/2015','DD/MM/YYYY'), 'CABA', '990');
insert into alquila values(32, 'aka654', to_date('14/02/2015','DD/MM/YYYY'), 'VIEDMA', to_date('15/02/2015','DD/MM/YYYY'), 'VIEDMA', '200');
insert into alquila values(27, 'omg987', to_date('20/03/2015','DD/MM/YYYY'), 'ROSARIO', to_date('22/03/2015','DD/MM/YYYY'), 'LA PLATA', '600');
insert into alquila values(35, 'lol123', to_date('02/02/2015','DD/MM/YYYY'), 'MENDOZA', to_date('09/02/2015','DD/MM/YYYY'), 'CORDOBA', '1300');
insert into alquila values(32, 'lol123', to_date('05/01/2015','DD/MM/YYYY'), 'CORDOBA', to_date('08/01/2015','DD/MM/YYYY'), 'MENDOZA', '330');
insert into alquila values(27, 'fyi654', to_date('04/02/2015','DD/MM/YYYY'), 'CABA', to_date('07/02/2015','DD/MM/YYYY'), 'CABA', '850');
insert into alquila values(27, 'lol123', to_date('13/02/2015','DD/MM/YYYY'), 'CORDOBA', to_date('20/02/2015','DD/MM/YYYY'), 'CABA', '1200');
insert into alquila values(27, 'fyi654', to_date('10/02/2015','DD/MM/YYYY'), 'CABA', to_date('12/02/2015','DD/MM/YYYY'), 'CABA', '700');
insert into alquila values(12, 'mib111', to_date('08/04/2015','DD/MM/YYYY'), 'USHUAIA', to_date('09/04/2015','DD/MM/YYYY'), 'USHUAIA', '300');
insert into alquila values(12, 'mib111', to_date('15/04/2015','DD/MM/YYYY'), 'CORDOBA', to_date('17/04/2015','DD/MM/YYYY'), 'USHUAIA', '1200');
insert into alquila values(12, 'mib111', to_date('20/04/2015','DD/MM/YYYY'), 'USHUAIA');
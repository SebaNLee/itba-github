
-- copiado de Campus
WITH RECURSIVE auxi ( ID, name, containedBy, size, qty ) AS
(
SELECT ID, name, containedBy, size, 0
FROM file
WHERE size > 0
UNION ALL
SELECT folder.ID, folder.name, folder.containedBy, auxi.size,
case qty
when 0 then 1
else qty
end as qty -- suman solo cuando son files, sino repite el valor
FROM auxi JOIN folder ON auxi.containedBy = folder.ID
)
select ID, name, sum(size) as size, sum(qty)as qty
from auxi
where qty != 0
group by ID, name
having sum(size) < X 
order by size desc, qty asc, ID asc




-- tablas
CREATE TABLE folder (
ID int,
name varchar(100) not null,
containedBy int,
primary key(ID),
foreign key(containedBy) references folder
);
CREATE TABLE file (
ID int,
name varchar(100) not null,
containedBy int,
size int not null,
primary key(ID),
foreign key(containedBy) references folder
);
insert into folder values(100, '/', null);
insert into folder values(300, 'Temp', 100);
insert into folder values(400, 'Hadoop', 300);
insert into folder values(200, 'Conf', 400);
insert into folder values(600, 'Data', 400);
insert into folder values(800, 'D1', 400);
insert into folder values(900, 'D1', 600);
insert into folder values(1000, 'D2', 600);
insert into folder values(1100, 'Other', 200);
insert into file values(100,'a.yml', 900, 8800 );
insert into file values(200,'proof.data', 1000, 2000 );
insert into file values(300,'b.yml', 800, 800 );
insert into file values(400,'pghba.txt', 100, 8400 );
insert into file values(1200,'a.yml', 800, 200 );
insert into file values(600,'a.yml', 1000, 2400 );
insert into file values(1300,'error.txt', 100, 0 );
insert into file values(1600,'noidea.txt', 1000, 0 );
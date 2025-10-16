

-- copio soluciones de Drive
-- 2.1
select producto.descripcion, count(satisfaccion)
from producto,log
where producto.prodid = log.prodid
group by producto.prodid, producto.descripcion
having count(satisfaccion) >= all (select count(*) from log
                                   group by prodid)

-- 2.2
select nombre
from cliente,log 
where cliente.clientid = log.clientid
and extract(year from log.fecha) < 2004
and extract(month from log.fecha) < 9

-- 2.3
select producto.descripcion, avg(coalesce(satisfaccion,-5)),case
                            when avg(coalesce(satisfaccion,-5)) = 10 then 'excelente'
                            when avg(coalesce(satisfaccion,-5)) >= 8 then 'muy bueno'
                            when avg(coalesce(satisfaccion,-5)) >= 6 then 'bueno'
                            when avg(coalesce(satisfaccion,-5)) >= 4 then 'regular'
                            when avg(coalesce(satisfaccion,-5)) >= 1 then 'malo'
                            end cartel
from producto, log 
where producto.prodid = log.prodid
group by log.prodid, producto.descripcion
having count(coalesce(satisfaccion,-5)) >= 3

-- 2.4
select distinct e1.nombre
from log l0, encuestador e1
where l0.encid = e1.encid
and l0.encid not in (select l1.encid 
                     from log l1 join log l2
                     on l1.clientid = l2.clientid                     
                     where l1.encid <> l2.encid)


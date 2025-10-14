

-- 4.1
-- selecciono primer mes para cada producto
SELECT pr.prodID, MIN(date) as first_month
FROM po JOIN deo ON deo.orderID = po.orderID RIGHT OUTER JOIN pr ON pr.prodID = deo.prodID
GROUP BY pr.prodID

-- ahora le meto orden y meto 'No vendido'
SELECT prodID as prodid, COALESCE(TO_CHAR(first_month, 'YYYY-MM'), 'NO VENDIDO')
FROM (
    SELECT pr.prodID, MIN(date) as first_month
    FROM po JOIN deo ON deo.orderID = po.orderID RIGHT OUTER JOIN pr ON pr.prodID = deo.prodID
    GROUP BY pr.prodID
)
ORDER BY EXTRACT(MONTH from first_month), EXTRACT(YEAR from first_month), prodID ASC


-- 4.2
-- cuento meses-años diferentes
SELECT COUNT(DISTINCT TO_CHAR(date, 'MM-YYYY'))
FROM po

-- productos que fueron vendidos todos los meses-años
SELECT prodID, COUNT(DISTINCT TO_CHAR(date, 'MM-YYYY')) as qty_month
FROM deo JOIN po ON deo.orderID = po.orderID
GROUP BY deo.prodID
HAVING COUNT(DISTINCT TO_CHAR(date, 'MM-YYYY')) = (
    SELECT COUNT(DISTINCT TO_CHAR(date, 'MM-YYYY'))
    FROM po
)
ORDER BY qty_month


-- 4.3
-- prodIDs que fueron vendidos ONLINE
SELECT prodID
FROM po JOIN deo ON deo.orderID = po.orderID
WHERE channel LIKE 'ONLINE'
GROUP BY prodID

-- prodIDs que fueron vendidos TIENDA
SELECT prodID
FROM po JOIN deo ON deo.orderID = po.orderID
WHERE channel LIKE 'TIENDA'
GROUP BY prodID

-- prodIDs vendidos ONLINE pero no en TIENDA
SELECT prodID
FROM po JOIN deo ON deo.orderID = po.orderID
WHERE prodID IN (
    SELECT prodID
    FROM po JOIN deo ON deo.orderID = po.orderID
    WHERE channel LIKE 'ONLINE'
    GROUP BY prodID
)
AND prodID NOT IN (
    SELECT prodID
    FROM po JOIN deo ON deo.orderID = po.orderID
    WHERE channel LIKE 'TIENDA'
    GROUP BY prodID
)
GROUP BY prodID




-- tablas
CREATE TABLE pr
(
    prodID INTEGER,
    stock INTEGER,

    PRIMARY KEY(prodID)
);

CREATE TABLE po
(
    orderID VARCHAR(10),
    date DATE,
    channel VARCHAR(10),

    PRIMARY KEY(orderID)
);

CREATE TABLE deo
(
    orderID VARCHAR(10),
    prodID INTEGER,
    qty INTEGER,

    FOREIGN KEY(prodID) REFERENCES pr,
    FOREIGN KEY(orderID) REFERENCES po
);

INSERT INTO pr VALUES(10, 4);
INSERT INTO pr VALUES(20, 8);
INSERT INTO pr VALUES(33, 12);
INSERT INTO pr VALUES(40, 8);
INSERT INTO pr VALUES(90, 8);
INSERT INTO pr VALUES(60, 8);
INSERT INTO pr VALUES(41, 10);

INSERT INTO po VALUES('A1', '2024-06-01', 'ONLINE');
INSERT INTO po VALUES('B1', '2025-06-02', 'TIENDA');
INSERT INTO po VALUES('C1', '2024-09-05', 'TIENDA');
INSERT INTO po VALUES('D1', '2025-09-15', 'ONLINE');

INSERT INTO deo VALUES('A1', 10, 100);
INSERT INTO deo VALUES('A1', 33, 200);
INSERT INTO deo VALUES('A1', 40, 200);
INSERT INTO deo VALUES('B1', 20, 40);
INSERT INTO deo VALUES('B1', 40, 800);
INSERT INTO deo VALUES('C1', 33, 1000);
INSERT INTO deo VALUES('C1', 20, 2000);
INSERT INTO deo VALUES('C1', 40, 3000);
INSERT INTO deo VALUES('D1', 10, 1110);
INSERT INTO deo VALUES('D1', 20, 1110);
INSERT INTO deo VALUES('D1', 33, 1110);
INSERT INTO deo VALUES('D1', 40, 1110);

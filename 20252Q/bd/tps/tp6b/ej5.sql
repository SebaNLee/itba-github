


-- 5.2
SELECT nombre
FROM alu
WHERE legajo NOT IN (
    SELECT legajo
    FROM examen
    WHERE nota >= 4
    GROUP BY legajo
    HAVING COUNT(*) >= (
        SELECT COUNT(*)
        FROM materia
    )
)

-- 5.3
SELECT legProf
FROM examen
GROUP BY legProf
HAVING COUUNT(DISTINCT legajo) = (
    SELECT COUNT(*)
    FROM alu
)

-- 5.4
SELECT legProf AS legajo, codigo
FROM examen
GROUP BY legProf, codigo
HAVING COUNT(DISTINCT legajo) = (
    SELECT COUNT(*)
    FROM alu
)

-- 5.5
SELECT nroActa
FROM examen
GROUP BY nroActa
HAVING COUNT(DISTINCT legProf) > 1

-- 5.6
UPDATE examen SET legProf = 239 WHERE nroActa = 113 OR nroActa = 111
-- ...

-- 5.7
SELECT legProf
FROM examen
WHERE legajo IN (
    SELECT legajo
    FROM examen
    WHERE nota >= 4
    GROUP BY legajo
    HAVING COUNT(DISTINCT codigo) = COUNT(
        SELECT COUNT(*)
        FROM materia
    )
)

-- 5.8
SELECT legajo, AVG(nota)
FROM examen
WHERE legajo IN (
    SELECT legajo
    FROM examen
    WHERE nota >= 4
    GROUP BY legajo
    HAVING COUNT(DISTINCT codigo) = (
        SELECT COUNT(*)
        FROM materia
    )
)
GROUP BY legajo

-- 5.9
-- ??
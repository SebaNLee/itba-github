


-- 6.2
WITH RECURSIVE familiar(X, Y) AS (
    SELECT persona, parienteDe FROM tabla1
    UNION
    SELECT familiar.X, tabla1.parienteDe
    FROM familiar, tabla1
    WHERE familiar.Y = tabal1.persona
)
SELECT X, Y FROM familiar


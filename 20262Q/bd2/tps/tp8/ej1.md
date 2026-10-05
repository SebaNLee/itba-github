

#### a. 

fallaría: `GRANT DELETE ON parrafo TO doc;`

pues no usuario `adm` no tiene permisos para otorgar DELETE (se le dio permisos sin WITH GRANT OPTION)


#### b.

`REVOKE SELECT ON parrafo FROM adm CASCADE;`, usuario `adm` y `doc` ambos pierden, el segundo por CASCADE

`REVOKE UPDATE(tiempo) ON parrafo FROM adm CASCADE;`, idem, solo pierden acceso a la columna tiempo, resto conserva

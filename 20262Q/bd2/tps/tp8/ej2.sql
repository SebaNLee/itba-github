
-- a.
GRANT ALL ON institucion TO u1 WITH GRANT OPTION;

-- b.
GRANT SELECT ON voluntario TO u2;

-- c.
GRANT INSERT ON voluntario TO u2;

-- d.
GRANT ALL ON tarea TO PUBLIC;

-- e.
REVOKE DELETE ON institucion FROM u1;

-- f.
REVOKE INSERT ON tarea FROM tarea;
-- solamente el admin/root podría hacer modificaciones

-- g.
CREATE ROLE 'ins_vol';
GRANT UPDATE(horas_aportadas) ON voluntario TO ins_vol;

-- h.
CREATE USER u4 IDENTIFIED BY 'password';
GRANT 'ins_vol' TO u3, u4;

-- i.
GRANT UPDATE(nombre) ON voluntario TO 'ins_vol';

-- j.
DROP ROLE 'ins_vol';
-- u3 y u4 pierden permisos de este rol, porque se elimina



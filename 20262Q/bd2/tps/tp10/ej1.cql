

DROP KEYSPACE IF EXISTS blog;
CREATE KEYSPACE blog WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE blog;

-- a.
-- diseño por partition key nombres de usuario (por búsquedas por usuario)
-- fecha de publicación como clustering key
-- para comentarios, por día partition y fecha el cluster

-- esquema copiado de Drive resolución
CREATE TABLE usuarios (
    nombre TEXT,
    nombre_usuario TEXT,
    cuenta_x TEXT,
    descripcion TEXT,
    PRIMARY KEY (nombre_usuario)
);

CREATE TABLE usuarios_x (
    cuenta_x TEXT,
    nombre TEXT,
    nombre_usuario TEXT,
    descripcion TEXT,
    PRIMARY KEY (cuenta_x)
);

CREATE TABLE noticias (
    usuario_aut TEXT,
    fecha_pub TIMEUUID,
    noticia_id UUID,
    titulo TEXT,
    cuerpo TEXT,
    descripcion TEXT,
    tags SET<TEXT>,
    PRIMARY KEY (usuario_aut, fecha_pub)
);

CREATE TABLE comentarios (
    dia DATE,
    fecha TIMEUUID,
    comentario_id UUID,
    autor TEXT,
    comentario TEXT,
    titulo_noticia TEXT,
    noticia_id UUID,
    PRIMARY KEY (dia, fecha)
);


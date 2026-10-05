# Base de Datos 2 - 72.41

## Estructura

Uso `_source` tanto para código de cátedra como propios (inicializadores de DBs).

## Comandos útiles


### Dev env:

#### MySQL

```
# Bajar imagen de Docker
docker pull mysql:9.7.2

# Levantar contenedor
docker run --name MyMySql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=mydb -e MYSQL_USER=myuser -e MYSQL_PASSWORD=mysecretpassword -p 3306:3306 -d mysql:9.7.2

# Entrar al contenedor
docker exec -it MyMySql bash

# Acceder a MySQL
mysql -u root -p

# Comandos útiles
SHOW DATABASES;
USE <db>;
SHOW TABLES;
```

> Nota: tener esto levantado y usar GUI desde W11 (DBeaver, MySQL Workspace, et al); con WSL networkingMode=mirrored

Flujo normal:

```
docker start MyMySql
docker exec -i MyMySql mysql -u root -proot mydb < script.sql
```

> Y tener DBeaver abierto...

#### MongoDB

```
# Bajar imagen de Docker
docker pull mongo:9.0.2

# Levantar contenedor
docker run --name Mymongo -p 27017:27017 -d mongo

# Entrar al contenedor
docker exec -it Mymongo bash

# Acceder a Mongo
mongosh

# Comandos útiles
db.getCollectionNames()
```

Flujo normal:

```
docker start Mymongo
docker exec -i Mymongo mongosh mydb < script.mongodb
```

### Extras:

```
docker ps -a
docker images
docker exec -it <contenedor> bash
```

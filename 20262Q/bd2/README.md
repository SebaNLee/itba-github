# Base de Datos 2

## Comandos útiles


#### Dev env:

```
# Bajar imagen de Docker
docker pull mysql:9.7.2

# Levantar contenedor
docker run --name MyMySql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=mydb -e MYSQL_USER=myuser -e MYSQL_PASSWORD=mysecretpassword -p 3306:3306 -d mysql:9.7.2

# Entrar al contenedor
docker exec -it MyMySql bash
```

> Nota: tener esto levantado y usar GUI desde W11 (DBeaver, MySQL Workspace, et al); con WSL networkingMode=mirrored

#### Dentro del contenedor:

```
# Acceder a MySQL
mysql -u root -p
```

#### MySQL:

```
SHOW DATABASES;
USE <db>;
SHOW TABLES;
```

#### Extras:

```
docker ps -a
docker images
docker exec -it <contenedor> bash
```



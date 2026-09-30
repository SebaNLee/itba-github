
Correr con:

```
mvn clean package
mvn spring-boot:run
```

### ej1

```
curl -X POST http://localhost:8080/graphql -H "Content-Type: application/json" -d '{"query":"{ recentPosts(count:2, offset:2){ id title } }"}'
```

Esperable que no ande, falta el @QueryMapping del ej2

### ej2

Ahora sí anda:

```
curl -X POST http://localhost:8080/graphql -H "Content-Type: application/json" -d '{"query":"{ recentPosts(count:2, offset:2){ id title } }"}'
```

Se suma:

```
curl -X POST http://localhost:8080/graphql -H "Content-Type: application/json" -d '{"query":"{ recentPosts(count:5, offset:0){ id title author { id name } } }"}'
```

### ej3

Se implementa el field resolver con @SchemaMapping, pero genera N+1

### ej4

Se implementa el field resolver con DataLoader, evita el N+1

### ej5

Implementa batch loading

(este no lo hago)



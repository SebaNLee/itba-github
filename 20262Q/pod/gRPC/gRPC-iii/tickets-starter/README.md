# Clase 6 - gRPC Tickets

## Spring gRPC streaming, Error Handling e Interceptors

### Tecnologías utilizadas
- JDK 25
- Maven 3
- Spring gRPC 1.0.2

**Documentación de Spring gRPC:** https://docs.spring.io/spring-grpc/reference/index.html

### Build

```shell
./mvnw clean package
```

### Estructura del proyecto

#### api

- `tickets.proto`: con la definición del servicio TrainTicketService

#### server

- `Servant.java`: heredando de TrainTicketServiceGrpc.TrainTicketServiceImplBase
- `Server.java`: Main
- `ServerConfig.java`: Para inicializar los server interceptors y el exception handler
- `HealthConfig.java`: Para inicializar el Health Service que viene por defecto
- `application.yml`: puerto y nivel de log (Opcional)
- `repository\`: Lógica con datos ficticios
- `interceptor\`: Server Interceptors
- `errorHandling\`: Exception Handler
- `TicketTest.java`: Test Unitario

#### client

- `Client.java`: Main
- `TicketServiceClient.java`: Lógica del cliente
- `ClientConfig.java`: Con los beans de los stub, los client interceptors
- `application.yml`: host, puerto y nivel de log (Opcional)
- `interceptor\`: Client Interceptors

### Ejecución Server

Después de haber realizado el build

#### Docker

```shell
docker compose up --build
```

#### Dev

```shell
./mvnw -pl server -am spring-boot:run
```

#### Shell

```shell
cd server/target 
```
```shell
tar -xzf grpc-tickets-server-2026.1Q-bin.tar.gz 
```
```shell
cd grpc-tickets-server-2026.1Q 
```
```shell
chmod u+x run-server.sh 
```
```shell
sh run-server.sh 
```

### Ejecución Client

#### Docker

```shell
docker compose --profile client run --rm --build client
```

#### Dev

```shell
./mvnw -pl client -am spring-boot:run
```

#### Shell

```shell
cd client/target 
```
```shell
tar -xzf grpc-tickets-client-2026.1Q-bin.tar.gz 
```
```shell
cd grpc-tickets-client-2026.1Q 
```
```shell
chmod u+x run-client.sh 
```
```shell
sh run-client.sh 
```

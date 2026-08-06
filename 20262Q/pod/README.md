# Programación de Objetos Distribuidos - 72.42

### Maven

Compilar y ejecutar:

```
mvn compile && mvn exec:java -Dexec.mainClass="<package.Class>"
```

Para tests:

```
mvn test -Dtest=<TestClass>
mvn test -Dtest=<TestClass>#<method>
```
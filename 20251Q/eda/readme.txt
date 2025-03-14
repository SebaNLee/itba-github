tuve problemas con Maven


los archivos normales funcionan normalmente
los de maven tiran error de classpath, ignorar


para ejecutar proyectos de Maven, compilar y ejecutar con (en el directorio root del proyecto):
    mvn compile
    mvn exec:java -Dexec.mainClass="com.example.Main"
    mvn test

obs.: para dependencias locales, se debe tener el archivo del dependency en cuestión compilado
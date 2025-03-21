tuve problemas con Maven


los archivos normales funcionan normalmente
los de maven tiran error de classpath, ignorar


primero, los proyectos normales de java deben ser subproyectos de la carpeta itba-github (se puede ver en JAVA PROJECTS, abajo izquierda)
esto se puede hacer primero todos los proyectos de Maven, e hacer un refresh del workspace de JAVA PROJECTS

para ejecutar proyectos de Maven, compilar y ejecutar con (en el directorio root del proyecto):
    mvn compile && mvn exec:java -Dexec.mainClass="com.example.Main"

para testeos:
    mvn test

para custom ram:
    mvn exec:java -Dexec.mainClass="com.example.Main" -Dexec.args="-Xms256m -Xmx1024m"


obs.: para dependencias locales, se debe tener el archivo del dependency en cuestión compilado
obs.: para que Intellisense pueda recomendar librerías, primero compilar el código (ejecuta y descarga dependencias del pom)
log de instalaciones xml:

?/8/2024

archivos de instalación en $home/XML

editado la carpeta de bashrc.txt (archivo que se ejecuta cada vez que se inicia sesión) (está oculto, ver con ls -a), agregando:

export CLASSPATH=$CLASSPATH:/home/sebalee/XML/xerces-2_12_1/xercesSamples.jar:/home/sebalee/XML/xerces-2_12_1/xml-apis.jar:/home/sebalee/XML/xerces-2_12_1/xercesImpl.jar
export PATH=$PATH:/usr/lib/jvm/java-7-openjdk-amd64/


el archivo de ejemplo a copiar tenía la versión del programa mal, lo tuve que editar



2/10/2024

archivos de instalación en $home/XML

a bashrc.txt agrego:

export CLASSPATH=$CLASSPATH:/home/sebalee/XML/SaxonHE11-3J/saxon-he-11.3.jar


el archivo de ejemplo a copiar tenía la versión del programa mal, descargué el nuevo por la página y lo tuve que editar en la línea agregada a bashrc.txt

testeo con:

java net.sf.saxon.Query consultaXP.xq
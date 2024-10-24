


extensiones instaladas (java):

    Language Support for Java, Red Hat
    Test Runner for Java, Microsoft
    Debugger for Java, Microsoft
    Project Manager for Java, Microsoft
    PlantUML

    PlantUML, estoy usandolo con servidor (necesito wifi), editado en configuración
    https://www.plantuml.com/plantuml/


instalación para Ruby:

    extensiones:
        Ruby Solargraph (Castwide)
        ruby-rubocop (misogi)
        Code Runner (Jun Han)

    gemas correspondientes:
        gem install solargraph
        gem install rubocop

    edito la cantidad de espacios por tab para archivos .rb:
        agregar lo siguiente en: settings->Editor: Ident SIze-> Edit in settings.json
        "[ruby]": {
            "editor.tabSize": 2,
            "editor.insertSpaces": true
        }

    comandos (ChatGPT):

        sudo apt update
        sudo apt upgrade

        sudo apt install -y curl gpg build-essential libssl-dev libreadline-dev libz-dev libffi-dev

        sudo apt install -y gnupg2

        gpg --keyserver hkp://keyserver.ubuntu.com --recv-keys 409B6B1796C275462A1703113804BB82D39DC0E3 7D2BAF1CF37B13E2069D6956105BD0E739499BDB

        \curl -sSL https://get.rvm.io | bash -s stable

        source ~/.rvm/scripts/rvm

        rvm -v

        rvm install 3.3.5

        ruby -v

        gemas extra instaladas:
            gem install sorted_set (para sorted_set, que no es biblioteca estándar)





//////////////////////////////////////////////





problema de compilación:

1/8/2024

mentalidad instalación: 
instale el jdk y rubydevikit en windows, con programas en windows corriendo desde windows
accedería a wsl desde windows


9/8/2024

tuve problemas con IntelliJ que compilaba y corría bien las primeras dos veces, 
pero reutilizaba la última compilación desde el 2do intento

según lo invetigado, parece que tiene problemas al acceder a wsl
además, correr código fuente en Windows no daba problemas

también, había explorado opciones de conexión remota con IntelliJ, pero era lento e incómodo

entonces, el IntelliJ lo usaría solo para Windows


10/8/2024

planteado y desarrollado ideas de tener doble directorio en WSL y Windows, pero abandono la idea por ser incómodo (varias materias)

parece que había problemas proque abría como proyecto una carpeta global en donde abría varios subproyecto adentro
IntelliJ compartía la carpeta ejecutable /out para todos los proyecto
VSCode generaba comportamiento raro al tener proyectos con ejecutables con el mismo nombre

estos dos posibles causas, posiblemente aislados, generaban el mismo problema

CONCLUSIÓN:
uso VSCode por ahora, a ver hasta dónde llega


14/8/2024

HIPÓTESIS: package, lo vi en clase que se creaba varios proyectos en un mismo directorio, pero con package

24/8/2024

veo que el problema viene dado porque al clickear el botón de correr código, se necesita declarar en un json con el sourcepath
el comando de click derecho de new java project no generaba el problema por crear el json automáticamente
ahora, se generan problemas al crear directamente el .java como un archivo individual, pues no tiene el json

CONCLUSIÓN: creo para todo ejercicio un proyecto de java por ejercicio y meto todos los incisos dentro de src

PENDIENTE: todavía no sé qué hace package, tal vez sea la solución óptima

30/8/2024

logré usar package correctamente
funciona si hago referencia desde src/... 
pero nofunciona de 20242Q/poo/...

CONCLUSIÓN: uso package para incisos de ejercicios


10/10/2024

instalación de entorno de Ruby
modifiqué el compilado de extensiones y comandos de arriba

rbenv, sirve para gestión de versiones de Ruby
no funcionó

usé RVM para instalar Ruby
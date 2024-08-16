


extensiones instaladas:

Language Support for Java, Red Hat
Test Runner for Java, Microsoft
Debugger for Java, Microsoft
Project Manager for Java, Microsoft
PlantUML

PlantUML, estoy usandolo con servidor (necesito wifi), editado en configuración
https://www.plantuml.com/plantuml/



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
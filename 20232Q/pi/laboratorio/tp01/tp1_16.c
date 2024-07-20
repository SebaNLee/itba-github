#include <stdio.h>

int main(void){
    int i;
    int i;
    printf("hola, esta es una función simple %d\n", i);
}






/* 1. no hay problema si no hay cosas como printf, pero podría fallar la compilación si hay cosas como printif sin el archivo header
** 2. hay problema en la linkedición cuando se juntan dos main
** 3. de nuevo, problemas en la linkedición
** 4. en linkedición, puede compilar código sin main, pero no sabría por dónde empezar en enlace
** 5. error de compilación, no se puede en C
** 6. no se puede redefinir un tipo de variable, sea igual o diferente
** 7. va a depender del sistema operativo, podría agregar un return 0 al final o no
** 8. error de compilación
*/
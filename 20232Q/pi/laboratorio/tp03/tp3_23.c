#include <stdio.h>
#include "getnum.h"

int main(void){

    int n, c;
    /* al salir del ciclo n debe ser un número par mayor que cero */
    do
        n = getint("");
    while(n != EOF && (n % 2 == 0) && n > 0);

    /* este ciclo debe incluir la lectura de caracteres y debe ejecutarse
    * mientras no sea fin de archivo y los caracteres sean dígitos o minúsculas
    */
    while ((c = getchar()) != EOF && (n >= 'a' && n <= 'z') && (n >= '0' && n <= '9'))
    putchar(c);




    return 0;
}
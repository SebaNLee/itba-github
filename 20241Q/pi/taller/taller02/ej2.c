#include <stdio.h>
#include "getnum.h"

int main(void)
{
    int resultado = 0, input, saveInput;

    input = getint("Ingrese número para sumar sus cifras: ");

    saveInput = input; // para guardar la variable original por si me lo piden después

    while(input != 0)
    {
        resultado += (input % 10);
        input = input / 10;
    }

    printf("Resultado: %d\n", resultado);

    return 0;
}

// gcc ej2.c getnum.c -o ej2 -Wall -std=c99 -pedantic -lm
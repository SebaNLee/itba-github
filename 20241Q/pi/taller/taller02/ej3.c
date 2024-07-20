#include <stdio.h>
#include "getnum.h"

int main(void)
{
    float input;
    int parteEntera, resultado = 0;

    input = getfloat("Número para sumar cifras decimales: ");

    while(input != 0)
    {
        // trunco la parte entera
        parteEntera = (int) input;
        input = input - parteEntera;

        input = input * 10;

        resultado += (int) input; // sumo la parte entera
    }

    printf("Resultado: %d\n", resultado);
}

// gcc ej3.c getnum.c -o ej3 -Wall -std=c99 -pedantic -lm
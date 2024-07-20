#include <stdio.h>
#include <math.h>
#include "getnum.h"

int main(void)
{
    float a = 0, b = 0, c = 0, discriminante, raiz, resultado1, resultado2;

    a = getfloat("a: ");
    b = getfloat("b: ");
    c = getfloat("c: ");

    if(a == 0)
    {
        printf("No es ecuación cuadrática\n");
        return 1;
    }

    discriminante = b * b - 4 * a * c;

    if(discriminante < 0)
    {
        printf("Error raíces complejas\n");
        return 1;
    }

    raiz = sqrt(discriminante);

    resultado1 = (-b + raiz) / (2 * a);
    resultado2 = (-b - raiz) / (2 * a);

    printf("Resultado1 = %f\n", resultado1);
    printf("Resultado1 = %f\n", resultado2);

    return 0;
}

// gcc ej1.c getnum.c -o ej1 -Wall -std=c99 -pedantic -lm

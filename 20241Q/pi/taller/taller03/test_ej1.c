#include <stdio.h>
#include <assert.h>
#include <math.h>
#include "getnum.h"
 
int esPrimo(int);
 
int
main(void) {
    assert(!esPrimo(0)); // 0 no es primo (no es natural)
    assert(!esPrimo(1)); // 1 no es primo
    assert(esPrimo(2)); // 2 es primo
    assert(esPrimo(3));
    assert(!esPrimo(4));
    assert(esPrimo(5));
    assert(!esPrimo(6)); // Equivalente a assert(esPrimo(6) == 0)
    assert(esPrimo(7)); // Equivalente a assert(esPrimo(7) == 1)
    printf("OK!\n");
    return 0;
}

// copio la función desde ej1.c para el test
int esPrimo(int input)
{
    double cribaEra;

    // el 1 no es primo
    if (input == 1)
    {
        return 0;
    }

    // 2 es primo
    if (input == 2)
    {
        return 1;
    }

    // si es par no va a ser primo
    if (input % 2 == 0)
    {
        return 0;
    }

    cribaEra = sqrt(input);

    // recorro los impares, desde el 3, hasta la cota de la criba de Eratóstenes
    for(int i = 3; i <= cribaEra; i = i + 2)
    {
        if((input % i) == 0)
        {
            return 0;
        }
    }

    return 1;
}
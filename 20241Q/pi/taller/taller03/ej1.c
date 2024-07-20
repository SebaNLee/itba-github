#include <math.h>
#include <stdio.h>
#include "getnum.h"

int esPrimo(int input);

int main(void)
{
    int input, result;

    input = getint("Ingrese un número (para ver si es primo): ");

    result = esPrimo(input);

    if(result == 1)
    {
        printf("Es primo\n");
        return 0;
    }
    else
    {
        printf("No es primo\n");
        return 0;
    }
}

int esPrimo(int input)
{
    double cribaEra;

    // el 1 no es primo
    if (input == 1)
    {
        return 0;
    }

    // si es par va a ser primo
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

// falta implementar para que chequee negativos también, que se hace fácilmente con un módulo (abs())
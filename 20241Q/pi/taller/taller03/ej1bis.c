#include <math.h>
#include <stdio.h>
#include "getnum.h"

int esPrimo(int input);

int main(void)
{
    int n, count = 0, temp;

    n = getint("Ingrese la cantidad de primos a generar: ");

    for(int i = 0; count != n; i++)
    {
        temp = esPrimo(i);

        if(temp == 1)
        {
            printf("%d; ", i);
            count++;
        }
    }
    printf("\n");

    return 0;
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
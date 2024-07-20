#include <stdlib.h>

int suma(int numero)
{
    if(numero == 0)
    {
        return 0;
    }

    return abs(numero % 10) + suma(numero / 10); // ojo el abs(), para los negativos
}





// gcc ej2.c tests/tp09_ej02_test.c -o ej2 -Wall -pedantic -std=c99
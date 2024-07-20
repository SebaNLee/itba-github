#include <string.h>

void invierte(char * origen, char * destino)
{
    if(origen[0] == '\0')
    {
        return;
    }

    destino[strlen(origen) - 1] = origen[0];

    invierte(origen + 1, destino);
}

// gcc ej10.c tests/tp09_ej10.c -o ej10 -Wall -pedantic -std=c99
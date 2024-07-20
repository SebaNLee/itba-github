#include <stdio.h>
#include <stdlib.h>
#include "getnum.h"

#define BLOQUE 5

float * readArray(float * ptr, int * dim);

int main(void)
{

    float * ptr = NULL;
    int dim = 0;

    ptr = readArray(ptr, &dim);

    for(int i = 0; i < dim; i++)
    {
        printf("%f ", ptr[i]);
    }
    
    printf("\n");

    free(ptr);

    return 0;
}

float * readArray(float * ptr, int * dim)
{
    float n;

    while ((n = getfloat("Ingrese un número: ")) != -1) // -1 para terminar
    {
        if(*dim % 5 == 0)
        {
            ptr = realloc(ptr, sizeof(float) * (*dim + BLOQUE));
        }

        ptr[(*dim)++] = n;
    }

    ptr = realloc(ptr, sizeof(float) * (*dim));

    return ptr;
}

// gcc ej1.c getnum.c -o ej1 -Wall -pedantic -std=c99 -fsanitize=address
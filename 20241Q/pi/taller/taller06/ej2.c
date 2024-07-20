#include <stdio.h>
#include <stdlib.h>
#include "getnum.h"

#define BLOQUE 5


int * cargarNumeros(int *dim);

int yaSalio(int * arr, int dim, int c);

int main(void)
{
    int dim;
    int *arr;
    arr = cargarNumeros(&dim);

    for(int i = 0; i < dim; i++)
    {
        printf("%d ", arr[i]);
    }

    printf("\n");

    free(arr);
    return 0;
}

int * cargarNumeros(int *dim)
{
    int * arr = NULL;
    int c;

    *dim = 0;
    while((c = getint("Ingresar un número: ")) != EOF && c != -1)
    {
        if(yaSalio(arr, *dim, c) == 0)
        {
            if(*dim % BLOQUE == 0)
            {
                arr = realloc(arr, (*dim + BLOQUE) * sizeof(int));
            }

            arr[(*dim)++] = c;
        }
        else
        {
            return arr;
        }
    }

    return arr;
}

int yaSalio(int * arr, int dim, int c)
{
    for(int i = 0; i < dim; i++)
    {
        if(arr[i] == c)
        {
            return 1;
        }
    }

    return 0;
}

// nota, al ser getint, si yo ingreso un número con varios dígitos lo cuenta como 1 elemento y no varios
// si es un getchar sí los contaría como caracteres individuales

// gcc ej2.c getnum.c -o ej2 -Wall -pedantic -std=c99
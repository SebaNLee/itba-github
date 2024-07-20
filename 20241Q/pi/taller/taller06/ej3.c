#include <stdio.h>
#include <stdlib.h>

#define BLOQUE 5

int main(void)
{
    char c;
    unsigned int dim = 0;
    char *arr = NULL;

    while((c = getchar()) != EOF)
    {
        if(dim % BLOQUE == 0)
        {
            arr = realloc(arr, (dim + BLOQUE) * sizeof(char));
        }

        arr[dim++] = c;
    }

    // imprimo para corroborar
    printf("\narr:\n%s", arr);

    free(arr);

    return 0;
}

// gcc ej3.c -o ej3 -Wall -pedantic -std=c99
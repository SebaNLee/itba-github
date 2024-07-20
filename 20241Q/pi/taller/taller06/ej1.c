#include <stdio.h>
#include <stdlib.h>

#define TAM 10

int main(void)
{
    int *arr1 = NULL, *arr2 = NULL, *arr3 = NULL;

    // con malloc    
    arr1 = malloc(TAM * sizeof(int));

    for(int i = 0; i < TAM; i++)
    {
        arr1[i] = 0;
    }

    // con realloc
    arr2 = realloc(arr2, TAM * sizeof(int));

    for(int i = 0; i < TAM; i++)
    {
        arr2[i] = 0;
    }


    // con calloc
    arr3 = calloc(TAM, sizeof(int));    


    // imprimo
    for(int i = 0; i < TAM; i++)
    {
       printf("arr1: %d | arr1: %d | arr1: %d\n", arr1[i], arr2[i], arr3[i]);
    }

    free(arr1);
    free(arr2);
    free(arr3);

    return 0;
}

// gcc ej1.c -o ej1 -Wall -pedantic -std=c99
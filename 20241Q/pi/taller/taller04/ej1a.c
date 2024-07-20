#include <stdio.h>
#include "random.h"

void mezclarVector(int v[], int dim);

int main(void)
{
    // vector para testeo, modificar como se quiera
    int v[] = {0, 1, 2, 3, 4};
    int dim = 5;

    mezclarVector(v, dim);

    for(int i = 0; i < dim; i++)
    {
        printf("%d ", v[i]);
    }
    printf("\n");

    return 0;
}


void mezclarVector(int v[], int dim)
{
    int randTemp, aux;
    
    randomize();

    for(int i = 0; i < dim; i++)
    {
        randTemp = randInt(0, dim - 1);
        aux = v[i];
        v[i] = v[randTemp];
        v[randTemp] = aux;
    }
}
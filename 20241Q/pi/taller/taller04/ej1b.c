#include <stdio.h>
#include "random.h"

void mezclarVector(int v[]);

int main(void)
{
    // vector para testeo, modificar como se quiera
    int v[] = {0, 1, 2, 3, 4, -1};

    mezclarVector(v);

    for(int i = 0; v[i] != -1; i++)
    {
        printf("%d ", v[i]);
    }
    printf("\n");

    return 0;
}


void mezclarVector(int v[])
{
    int randTemp, aux;
    
    randomize();

    // acá empiezo desde el 1
    // voy generando un número aleatorio de las posiciones válidas por dada por la iteración i
    // de esta manera evito tener que recorrer todo el vector antes para encontrar la dimensión
    for(int i = 1; v[i] != -1; i++)
    {
        randTemp = randInt(0, i);
        aux = v[i];
        v[i] = v[randTemp];
        v[randTemp] = aux;
    }
}
#include <stdio.h>

int dondeEsta(int v[], int dim, int num, int posV[]);

int main(void)
{
    // creo un vector y num a buscar para testeo, modificar como se desea
    int v[] = {0, 0, 1, 2, 2, 3, 3, 3, 4};
    int dim = sizeof(v) / sizeof(v[0]);
    int num = 3;

    // creo vector de posiciones
    int posV[dim];

    int result;

    result = dondeEsta(v, dim, num, posV);

    printf("Elemto a buscar: %d\n", num);
    printf("Posiciones: ");
    for(int i = 0; i < result; i++)
    {
        printf("%d ", posV[i]);
    }
    printf("\n");

    return 0;
}

int dondeEsta(int v[], int dim, int num, int posV[])
{
    int count = 0, j = 0;

    for(int i = 0; i < dim; i++)
    {
        if(v[i] == num)
        {
            count++;
            posV[j++] = i;
        }
    }

    return count;
}

// gcc ej2.c random.c -o ej2 -Wall -pedantic -std=c99
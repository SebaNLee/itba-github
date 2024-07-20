#include <stdio.h>

int interseccion(int v1[], int v2[], int dim1, int dim2, int resp[]);

int main(void)
{
    // declaro vectores y sus dim para testeo, modificar como se desee
    int v1[] = {7, 9, 3, 5, 15};
    int v2[] = {5, 6, 7, 9, 8};
    int dim1 = 5, dim2 = 5;

    int max = (dim1 > dim2) ? dim1 : dim2;

    int resp[max];

    int newDim;
    newDim = interseccion(v1, v2, dim1, dim2, resp);

    printf("Intersección: ");
    for(int i = 0; i < newDim; i++)
    {
        printf("%d ", resp[i]);
    }
    printf("\n");

}

int interseccion(int v1[], int v2[], int dim1, int dim2, int resp[])
{
    int count = 0, flagFound = 0, k = 0;

    for(int i = 0; i < dim1; i++)
    {
        flagFound = 0; // !! se debe resetear el flag, porque si no encuentra solamente a uno   

        for(int j = 0; j < dim2 && !flagFound; j++)
        {
            if(v1[i] == v2[j])
            {
                resp[k++] = v1[i];
                flagFound = 1;
                count++;
            }
        }
    }

    return count;
}


// gcc ej3a.c random.c -o ej3a -Wall -pedantic -std=c99
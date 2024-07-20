#include <stdio.h>

int isNull(int v[], int dim);

int main(void)
{
    //int v[] = {9, 3, 2, 3};
    int v[4] = {0}; 

    printf("%d \n", isNull(v, 4));

    return 0;
}

int isNull(int v[], int dim)
{
    if(dim == 0)
        return 1;
    
    if(*v == 0)
    {
        return isNull(v + 1, dim - 1);
    }

    return 0;
}
// hay una solución con menos sintaxis en ON


// gcc ej2.c -o ej2 -Wall -pedantic -std=c99
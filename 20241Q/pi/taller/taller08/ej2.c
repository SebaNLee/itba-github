#include <stdio.h>

int igualesRet(int v1[], int v2[]);

void igualesVoid(int v1[], int v2[], int * resultado);

int main(void)
{
    int v1[] = {1, 2, 3, 4, 5, -1};
    int v2[] = {1, 2, 3, 4, 5, -1};

    int v3[] = {1, 2, 3, 4, 5, -1};
    int v4[] = {2, 3, -1};

    printf("%d\n", igualesRet(v1, v2));
    printf("%d\n", igualesRet(v3, v4));

    int resultado;

    resultado = -1;
    igualesVoid(v1, v2, &resultado);
    printf("%d\n", resultado);
    
    resultado = -1;
    igualesVoid(v3, v4, &resultado);
    printf("%d\n", resultado);

    return 0;
}

// retorna 1 si son iguales, 0 si no son iguales
int igualesRet(int v1[], int v2[])
{
    if(*v1 == -1 && *v2 == -1)
    {
        return 1;
    }

    if(*v1 != *v2)
    {
        return 0;
    }

    return igualesRet(v1 + 1, v2 + 1);
}

void igualesVoid(int v1[], int v2[], int *resultado)
{
    if(*v1 == -1 && *v2 == -1)
    {
        *resultado = 1;
        return;
    }

    if(*v1 != *v2)
    {
        *resultado = 0;
        return;
    }

    igualesVoid(v1 + 1, v2 + 1, resultado);

    return;
}

// gcc ej2.c -o ej2 -Wall -pedantic -std=c99
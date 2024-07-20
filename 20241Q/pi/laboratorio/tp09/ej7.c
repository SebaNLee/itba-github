
int busquedaBinaria(int datos[], int dim, int num)
{
    if(dim <= 0)
    {
        return 0;
    }

    if(datos[dim / 2] == num)
    {
        return 1;
    }

    if(datos[dim / 2] > num)
    {
        return busquedaBinaria(datos + dim / 2 - 1, dim / 2, num);
    }

    return busquedaBinaria(datos, dim / 2, num);
}

// gcc ej7.c tests/tp09_ej07_test.c -o ej7 -Wall -pedantic -std=c99
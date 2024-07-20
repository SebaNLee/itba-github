

double productoEsc(double v1[], double v2[], int dim)
{
    if(dim == 0)
    {
        return 0;
    }

    return v1[0] * v2[0] + productoEsc(v1 + 1, v2 + 1, dim - 1);
}



// gcc ej3.c tests/tp09_ej03_test.c -o ej3 -Wall -pedantic -std=c99
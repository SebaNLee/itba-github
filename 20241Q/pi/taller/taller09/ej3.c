

typedef int (*fnInt) (int, int);

int operatoria(int v[], int dim, int base, fnInt func)
{
    if(dim <= 0)
    {
        return base;
    }

    return func(v[0], operatoria(v + 1, dim - 1, base, func));
}

// gcc ej3.c ej3test.c -o ej3 -Wall -pedantic -std=c99


typedef int (*fnInt) (int);

void transforma(int v[], int dim, fnInt f)
{
    if(dim <= 0)
    {
        return;
    }

    v[0] = f(v[0]);
    transforma(v + 1, dim - 1, f);
    return;
}

// gcc ej2.c ej2test.c -o ej2 -Wall -pedantic -std=c99
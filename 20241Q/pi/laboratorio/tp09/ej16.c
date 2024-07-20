

int orden(const int v[])
{
    // validaciones, no casos bases
    if(v[0] == -1 || v[1] == -1)
        return 0;

    // caso base
    if(v[2] == -1) // esta sería la primera comparación
    {
        if(v[0] > v[1])
            return -1;
        if(v[0] < v[1])
            return 1;
        return 0;
    }

    int aux = orden(v + 1);

    if(aux == 0)
        return 0;

    if(aux == -1 && v[0] > v[1])
        return aux;
    if(aux == 1 && v[0] < v[1])
        return aux;
    else
        return 0;
}

// gcc ej16.c tests/tp09_ej16_test.c -o ej16 -Wall -pedantic -std=c99
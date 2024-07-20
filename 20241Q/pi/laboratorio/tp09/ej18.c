

int sumMatch(const int v[])
{
    if(v[0] == -1)
        return 0;

    int aux = sumMatch(v + 1);

    if(aux == 0)
        return v[0];

    if(aux > 0)
        return aux - v[0];
    
    else
        return aux;
}

// gcc ej18.c tests/tp09_ej18_test.c -o ej18 -Wall -pedantic -std=c99
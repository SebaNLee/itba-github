#include <string.h>


// lo hago con wrapper para el size

int esPalindromoRec(const char * s, int dim)
{
    if(dim <= 1)
    {
        return 1;
    }

    if(s[0] != s[dim - 1])
    {
        return 0;
    }

    return esPalindromoRec(s + 1, dim - 2);
}

int palindromo(const char * s)
{
    int dim = strlen(s);

    return esPalindromoRec(s, dim);
}

// gcc ej6.c tests/tp09_ej06_test.c -o ej6 -Wall -pedantic -std=c99
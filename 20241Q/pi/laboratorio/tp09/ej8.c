#include <stdio.h>

int ackermann(int m, int n)
{
    if(m == 0)
    {
        return n + 1;
    }

    if(n == 0)
    {
        return ackermann(m - 1, 1);
    }
    return ackermann(m - 1, ackermann(m, n - 1));
}



// gcc ej8.c tests/tp09_ej08_test.c -o ej8 -Wall -pedantic -std=c99
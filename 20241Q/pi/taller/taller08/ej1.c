#include <stdio.h>

int esPotenciaDe3(int n);

int main(void)
{
    printf("%d\n", esPotenciaDe3(6));
}

int esPotenciaDe3(int n)
{
    if(n == 3 || n == 1)
    {
        return 1;
    }

    if(n % 3 != 0)
    {
        return 0;
    }



    return esPotenciaDe3(n / 3);
}

// gcc ej1.c -o ej1 -Wall -pedantic -std=c99
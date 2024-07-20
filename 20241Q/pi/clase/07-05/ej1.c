#include <stdio.h>

int mcdEuclides(int a, int b)
{
    if(b == 0)
    {
        return a;
    }

    return mcdEuclides(b, a % b);
}

int main(void)
{
    printf("%d\n", mcdEuclides(15, 5));

    return 0;
}

// gcc ej1.c -o ej1 -Wall -pedantic -std=c99
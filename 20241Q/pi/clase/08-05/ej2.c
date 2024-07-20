#include <stdio.h>

void printAltura(int n);

int main(void)
{
    printAltura(5);
    puts("");

    return 0;
}

void printAltura(int n)
{
    if(n >= 1)
    {
        printAltura(n / 2);
        printf("%d", n);
        printAltura(n / 2);

        return;
    }

    return;
}

// gcc ej2.c -o ej2 -Wall -pedantic -std=c99
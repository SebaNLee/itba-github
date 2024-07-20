#include <stdio.h>
#include "random.h"

#define ASCIIDIF 'a' - 'A'

int readText(int n, int c);

int main(void)
{
    int n, c, result;

    randomize();

    n = randInt(1, 256);
    c = randInt('A', 'Z');

    result = readText(n, c);

    printf("Letra generada: %c, Encontrados: %d\n", c, result);

    return 0;
}

int readText(int n, int c)
{
    int count = 0, input, i = 0;

    while((input = getchar()) != EOF && i < n)
    {
        if(input == c || input - (ASCIIDIF) == c)
        {
            count++;
        }

        i++;
    }

    return count;
}
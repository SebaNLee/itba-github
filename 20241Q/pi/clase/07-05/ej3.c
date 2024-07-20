#include <stdio.h>

int cantVocales(char v[]);
int isVowel(char c);

int main(void)
{
    char v[] = "HOLA";

    printf("%d\n", cantVocales(v));

    return 0;
}

int cantVocales(char v[])
{
    if(*v == '\0')
    {
        return 0;
    }

    return isVowel(*v) + cantVocales(v + 1);
}

int isVowel(char c)
{
    switch(c)
    {
        case 'A':
        case 'E':
        case 'I':
        case 'O':
        case 'U':
            return 1;
        
        default:
            return 0;
    }
}

// gcc ej3.c -o ej3 -Wall -pedantic -std=c99
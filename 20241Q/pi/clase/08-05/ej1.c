#include <stdio.h>
#include <string.h>

int esCapicua(char v[], int len);

int main(void)
{
    char v[] = "abcba";

    printf("%d\n", esCapicua(v, strlen(v)));

    return 0;
}


int esCapicua(char v[], int len)
{
    if(len <= 1)
    {
        return 1;
    }

    return (v[0] == v[len - 1]) && esCapicua(v + 1, len - 2);
}







// gcc ej1.c -o ej1 -Wall -pedantic -std=c99
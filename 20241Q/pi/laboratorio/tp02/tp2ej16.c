#include <stdio.h>

int main(void)
{
    char a, b;

    printf("Ingrese dos caracteres seguidos: \n");

    a = getchar();
    b = getchar();

    printf("El caracter %c es %s a %c\n", a, (a >= b) ? "mayor" : "menor", b);
}
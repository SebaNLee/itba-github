#include <stdio.h>

char letra;
void segundo(void);

int main(void)
{
    printf("Ingrese un carácter : ");

    letra = getchar();
    segundo();
    printf(" letra es : %c\n", letra);

    return 0;
}

void segundo(void)
{
    char letra;

    letra = 'X';
    return;
}

// imprime lo obtenido por getchar en l10
#include <stdio.h>

#define ISDIGIT(l) (((l) > '0' && (l) < '9') ? 1 : 0)

int main(void){


    int letra;

    letra = getchar();

    if ( ISDIGIT(letra) )
        printf("Es un dígito\n");




    return 0;
}
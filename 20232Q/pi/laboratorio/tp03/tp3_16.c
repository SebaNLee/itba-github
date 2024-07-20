#include <stdio.h>
#include "getnum.h"

int main(void){

    int decimal, base, resto = 0;

    decimal = getint("Ingrese un número entero positivo: ");

    printf("Equivalente en binario: ");

    for(; decimal != 0 ; decimal /= 2){

        resto = decimal % 2;

        printf("%d", resto);
    }

    printf("\n");


    return 0;
}

// me dan al revés los bits
// se podría arreglar con arreglos, no lo voy a hacer
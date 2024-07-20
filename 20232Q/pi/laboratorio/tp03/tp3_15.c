#include <stdio.h>
#include "getnum.h"

int main(void){

    int n, base, resto, decimal = 0;
    n = getint("Ingrese un número binario: ");

    for(base = 1; n > 0; n /= 10, base *= 2){
        if((resto = n % 10) > 1){
            printf("%d no es un número binario\n", n);
            return 1;
        }
        else if(resto){ // va a correrlo si es justo 1
            decimal += base;
        }
    }
    printf("En decimal: %d\n", decimal);


    return 0;
}
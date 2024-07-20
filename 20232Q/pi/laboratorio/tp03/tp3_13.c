#include <stdio.h>
#include "getnum.h"

int main(void){

    int lado, aux1, aux2, aux3;
    lado = getint("Ingrese el lado de un cuadrado: ");
    
    // a)

        for(aux1 = 1; aux1 <= lado; aux1++){
            for(aux2 = 1; aux2 <= lado; aux2++){
                printf("*");
            }
            printf("\n");
        }

    // b)

    for(aux3 = 1; aux3 <= lado * lado; aux3++){
        printf("*");
        if(aux3 % lado == 0){
            printf("\n");
        }

    }






    return 0;
}
#include <stdio.h>
#include "getnum.h"

int main(void){

    int num, aux, capi = 0;

    num = getint("Ingrese un número entero positivo: ");

    aux = num;

    while(aux > 0){
        capi = capi * 10 + aux % 10;
        aux /= 10;
    }

    printf("%d %s es capicúa\n", num, (num == capi) ? "sí" : "no");


    return 0;
}


// estrategia: doy vuelta el número y ==

#include <stdio.h>
#include <math.h>
#include "getnum.h"

int main(){

    int entrada, salida, num, aux, copiaNum, resBase, res;

    res = 0;

    printf("Ingeresar la bases con el siguiente formato: '<entrada>salida'\n");
    scanf("<%d>%d", &entrada, &salida);

    num = getint("Ingrese el número a convertir: ");

    copiaNum = num;

    for(int i = 0; num != 0; i++){

        aux = num % 10;
        resBase = aux * pow(i, entrada);
        num = num / 10;
    }


    for(int j = 0; ; j++){

        aux = resBase % salida;
        res = res + aux;
        resBase = resBase / 10;
        
    }


    printf("%d en base %d es: %d\n", copiaNum, salida, res);

    return 0;
}


// ???











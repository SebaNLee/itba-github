#include <stdio.h>
#include "getnum.h"

int main(void){

    int numz, cincoz = 0;

    numz = getint("Ingrese un número entero: ");

    if(numz < 0){
        numz *= -1;
    }

    for(; numz > 0; numz /= 10){
        if((numz % 10) == 5){
            cincoz++;
        }

    }
    printf("Tiene %d cinco%s\n", cincoz, (cincoz == 1) ? "" : "s");




    return 0;
}
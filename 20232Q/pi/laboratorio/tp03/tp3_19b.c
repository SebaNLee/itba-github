#include <stdio.h>
#include "getnum.h"

#define ERROR 0.000000001

int main(void){



    int cincosR = 0, cincosZ = 0, parteEntera =0, num, aux = 0;
    double parteReal;

    num = getdouble("Ingrese un número real: ");

    if(num < 0){ // si es negativo lo paso a positivo
        num *= -1;
    }

    parteEntera = (int) num; // separo la parte entera a var parteEntera

    while(parteEntera > 0){ // cuento cincos en parteEntera
        if((parteEntera % 10) == 5){
            cincosZ++;
        }
        parteEntera /= 10; 
    }

    printf("La parte entera tiene %d cinco%s\n", cincosZ, (cincosZ == 1) ? "" : "s");



    parteReal = num - parteEntera; // separo la parte decimal a var parteReal

    while(parteReal == ERROR){ // cuento los cincos en parteReal, NO FUNCIONÓ, 
        parteReal *= 10;
        aux = (int) parteReal;
        if(aux == 5){
            cincosR++;
        }
    }

    printf("La parte decimal tiene %d cinco%s\n", cincosR, (cincosR == 1) ? "" : "s");



 











    return 0;
}








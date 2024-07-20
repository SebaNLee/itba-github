#include <stdio.h>
#include "getnum.h"

int main(void){

    int factorial = 1, num;

    do{
        num = getint("Ingrese un número positivo: ");
    } while(num < 0);

    while(num > 0){
        factorial *= num--;
    }

    printf("Factorial es: %d\n", factorial);


    return 0;
}
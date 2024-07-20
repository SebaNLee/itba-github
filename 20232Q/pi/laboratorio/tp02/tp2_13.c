#include <stdio.h>
#include "getnum.h"

int main(void){

    int num1, num2;
    num1 = getint("Ingrese el primer entero: ");
    num2 = getint("Ingrese el primer entero: ");

    printf("%d %s es múltiplo de %d\n", num2, (( num2 % num1 == 0) ? "SI" : "NO"), num1);

// el primero es el chico, el factor

    return 0;
}
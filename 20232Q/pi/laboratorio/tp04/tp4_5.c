#include <stdio.h>
#include "getnum.h"

#define MAXIMO2(max, n1, n2) (max = ((n1) > (n2)) ? (n1) : (n2))



int main(void){

    int a, b, max;
    a = getint("Ingrese el primer número: "); 
    b = getint("Ingrese el segundo número: "); 

    printf("El mayor entre %d y %d es: %d\n", a, b, MAXIMO2(max, a, b));


    return 0;
}
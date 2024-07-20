#include <stdio.h>
#include "getnum.h"

int main(void){

    int n;
    n = getint("Ingrese un número entero: ");
    n >>=1;
    printf("%d\n", n);



    return 0;
}
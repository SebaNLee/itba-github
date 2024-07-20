#include <stdio.h>
#include "getnum.h"

int main(){
    int n;

    n = getint("Ingrese un número:");
    n = (n >= 0 && n <= 9) ? (n + '0'):(n >= 10 && n<= 15 ? (n + 'A' - 10:n));
    printf("\n HEXA: %c",n);
}


















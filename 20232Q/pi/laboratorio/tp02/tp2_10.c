#include <stdio.h>
#include "getnum.h"

int main(void){

    int n;
    n = getint("Ingrese segundos: ");
    printf("En horas: %d\n", n / 3600);
    printf("En minutos: %d\n", n / 60);
    printf("En segundos: %d\n", n);



    return 0;
}
#include <stdio.h>

#define ERRORMINIMODESEADO 0.000000001 // se va modificando el error deseado acá

int main(void){

    double valorE= 1, valorEAnterior= 0, factorial = 1;
    int ordenN = 0;

    printf("%-15s%-15s\n", "N", "e");

    do{
        ordenN++;
        factorial = factorial * ordenN;
        valorEAnterior = valorE;
        valorE = valorE + (1 / factorial);
        printf("%-15d%-15lf\n", ordenN, valorE );
    } while((valorE - valorEAnterior) > ERRORMINIMODESEADO);











    return 0;
}
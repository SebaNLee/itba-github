#include <stdio.h>
#include <math.h>
#include "getnum.h"
#define EPSILON 0.0000001


double exCalc(double x);


int main(void){

    double ex, total;

    double x = getdouble("Ingrese el valor de x para calcular exp(x): ");

    if(x < 0){
        printf("Es solo para positivos\n");
    }
    else{
        ex = exp(x);
        total = exCalc(x);
        printf("Resultado esperado: %lf | Valor calculado: %lf\n", ex, total);

    }

    return 0;
}


double exCalc(double x){

    double valorTotal = 1 + x, valorAnterior = 0;
    int factorial = 1;

    for(int i = 2; (valorTotal - valorAnterior) > EPSILON; i++){ 
        valorAnterior = valorTotal;
        factorial *= i;
        valorTotal += (pow(x, i) / factorial);
    }


    return valorTotal;
}

// mi código es técnicamente válido, pero no en todos los casos
// el error de EPSILON es arbitrario y podría varías para algunos valores
// !! entonces se puede corregirlo quitando la comparación de valorAnterior y reemplazándolo con el término sumado



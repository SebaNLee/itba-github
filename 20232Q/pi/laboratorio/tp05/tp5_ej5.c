#include <stdio.h>
#include <math.h>

#define EPSILON 0.00001


double potencia(double base, int exponente){

    double aux, resultado;

    if(fabs(base) < EPSILON && exponente => 0){
        return -1.0;
    }
    if(exponente < 0){
        aux = 1 / base;
        exponente *= -1;
    }
    else{
        aux = base
    }

    for(resultado = 1; exponente != 0; resultado *= aux, exponente--);

    return resultado;
}
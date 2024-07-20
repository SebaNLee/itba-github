#include <stdio.h>
#include "getnum.h"

int main(void){
    
    double ventas;

    do{
        ventas = getdouble("Ingrese la suma vendida en una semana: ");
    } while(ventas < 0);

    if(ventas < 1000){
        printf("El monto a recibir es:0 $%.2lf\n", ventas + 300);
    }
    else if(ventas < 2000 && ventas >= 1000){
        printf("El monto a recibir es:5 $%.2lf\n", ventas + ventas * 0.05 + 300);
    }
    else if(ventas < 4000 && ventas >= 2000){
        printf("El monto a recibir es:7 $%.2lf\n", ventas + ventas * 0.07 + 300);
    }
    else{
        printf("El monto a recibir es:9 $%.2lf\n", ventas + ventas * 0.09 + 300);
    }

   
    




    return 0;
}
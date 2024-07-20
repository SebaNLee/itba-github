#include <stdio.h>
#include <math.h>
#include "getnum.h"


int menu(void);

double redondeo(double num, int cant);


int main(void){

    double x = getdouble("Ingrese un número: ");

    int opcion;
    opcion = menu();

    switch(opcion){
        case 1:
            x = redondeo(x, 0);
            break;
        case 2:
            x = redondeo(x, 1);
            break;
        case 3:
            x = redondeo(x, 2);
            break;
        case 4:
            x = redondeo(x, 3);
            break; 
        default:
            printf("Opción inválida\n");
            break;
    }
    if(opcion >= 1 && opcion <= 4){
        printf("El número es %g\n", x);
    }

    return 0;
}


int menu(void){

    int opcion;
    
    printf("1 - Redondeo al entero más cercano\n");
    printf("2 - Redondeo a la décima más cercana\n");
    printf("3 - Redondeo a la centésima más cercana\n");
    printf("4 - Redondeo a la milésima más cercana\n");

    do{
        opcion = getint("Ingrese una opción: ");
    }while(opcion < 0);

    return opcion;
}


double redondeo(double num, int cant){

    long factor;

    for(factor = 1; cant; factor *= 10, cant--);

    return floor(num * factor + 0.5) / factor;
}

























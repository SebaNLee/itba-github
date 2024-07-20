#include <stdio.h>
#include "getnum.h"

int main(void){

    float num = getfloat("Ingrese un número: ");

    int suma, aux;
    suma = aux = 0;

    do{
        int entero = num;
        num -= entero;
        num * 10;
        aux = (int) num;
        suma += aux;

    }while(num > 0);

    
    printf("La suma de dígitos de la parte decimal es: %d\n", suma);


    return 0;
}

// debido a la precisión del float, se pierde algo de información
// estaría bien para cuentas en general, pero si es cifras como este ejemplo no
// se usa el getchar y el ASCII para solucionar esto


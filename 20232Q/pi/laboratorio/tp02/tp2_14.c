#include <stdio.h>
#include "getnum.h"

int main(void){

    int num1, num2;
    num1 = getint("Ingrese el primer número: ");
    num2 = getint("Ingrese el segundo número: ");

    printf("El promedio es %.2f\n", ((float) num1 + num2) / 2);
    printf("La suma es %d\n", num1 + num2);
    printf("El menor es %d\n", (num1 < num2) ? num1 : num2);
    printf("El mayor es %d\n", (num1 > num2) ? num1 : num2);
    printf("%s son iguales\n", (num1 == num2) ? "SÍ" : "NO");

    return 0;
}
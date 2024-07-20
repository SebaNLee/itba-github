#include <stdio.h>
#include "getnum.h"

int main(){
    int num1, num2, num3, max1, max2;
    num1 = getint("Ingresar num1:");
    num2 = getint("Ingresar num3:");
    num3 = getint("Ingresar num2:");

    max1 = num1 > num2 ? num1:num2;
    max2 = num2 > num3 ? num2:num3; /*se pudo haber usado la misma var max1 en este caso*/


    /* otra forma para hacerlo en una línea sería:
    ** numMax = num1 > num2 ? (num1 > num3 ? num1:num3):(num2 > num3 ? num2:num3)
    */  

    printf("El número máximo es: %d\n",max2);

}















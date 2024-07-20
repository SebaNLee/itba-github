#include <stdio.h>
#include "getnum.h"

int main(void){

    float mtss, kmh;
    mtss = getfloat("Ingrese una velocidad en m/s: ");
    kmh = mtss * 3.6;
    printf("Equivalente en km/h: %f\n", kmh);



    return 0;
}
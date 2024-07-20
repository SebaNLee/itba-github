#include <stdio.h>
#include "getnum.h"

#define MAXIMO2(max2, n1, n2) (max2 = ((n1) > (n2)) ? (n1) : (n2))
#define MAXIMO3(max3, n1, n2, n3) (max3 = ((MAXIMO2(maxDef, n1, n2) > (n3)) ? (maxDef) : (n3)))



int main(void){

    int a, b, c, maxDef;
    a = getint("Ingrese el primer número: "); 
    b = getint("Ingrese el segundo número: "); 
    c = getint("Ingrese el tercer número: "); 

    printf("El mayor entre %d, %d y %d es: %d\n", a, b, c, MAXIMO3(maxDef, a, b, c));


    return 0;
}

// viendo la solución, se puede usar una sola var que reemplace vars max3 y maxDef 
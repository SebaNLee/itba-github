#include <stdio.h>

void f2( float a ){

    float a;

    printf ("%f\n",a*a);
}

// a es parámetro float, pero se lo quiere reutilizar como var, no se puede
// directamente se escribe el printf con a*a

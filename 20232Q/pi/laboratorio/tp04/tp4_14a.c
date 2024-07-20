#include <stdio.h>
#include "getnum.h"

float f1 (float);
float funAuxiliar (float);
float f2(float);

int
main (void)
{
	float x, y, z;
	x= getfloat("Ingrese un n�mero real :");
	putchar('\n');

	y = f1(x);
	z = f2(x);

	printf("El doble absoluto de %g es %g\n",x, y);
	printf("El triple absoluto de %g es %g\n",x , z);
	return 0;
}

float
f1 ( float x)
{
	return 2 * funAuxiliar(x);
}



// hay que agregar getnum.h
// hay que agregar el prototipo de f2
// hay que eliminar una función funAuxiliar, que está repetida dos veces

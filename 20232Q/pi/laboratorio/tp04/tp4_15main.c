#include "getnum.h"
#include <stdio.h>
#include "tp4_15a.h"
#include "tp4_15b.h"

int
main(void)
{
	int x,y,z;

	x = getint("Ingrese un numero: ");
	y = getint("\nIngrese otro numero: ");
	z = getint("\nIngrese ultimo numero: ");

	printf("\nEl mayor es %d\n", mayor3(x,y,z));
	printf("El promedio es %.2g\n", promedio3(x,y,z));

	return 0;
}

// error por declarar y usar diferentes parametros del prototipo fAuxiliar
// lo mejor es volverlos locales a cada archivo con con static (agregar protipo)
// obviamente, eliminar los prototipos en los .h para que solo se queden y usen en respectivos .c
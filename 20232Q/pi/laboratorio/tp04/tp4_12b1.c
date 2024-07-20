#include <stdio.h>

int
main(void)
{
	double i = 5.42;
	
	i = neg(i);
	printf("%f\n",i);
	return 0;
}

// por default asume int si no hay prototipos
// erróneo por ser float, devuelve cualquier cosa
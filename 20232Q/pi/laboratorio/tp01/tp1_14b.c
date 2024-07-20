#include <stdio.h>

#define NRO 019 

int
main(void)
{
	long i = NRO ;
	printf("%ld\n",i);
	return 0;
}

// este no se puede compilar, porque al NRO se reconoce como constante entera octal, y 9 no pretenece a esse sitema de numeración
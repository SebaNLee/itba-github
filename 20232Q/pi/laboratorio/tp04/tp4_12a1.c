#include <stdio.h>

int
main(void)
{
	int c = 2;
	
	c = neg(c);
	printf("%c\n",c);
	return 0;
}

// devuelve -2, pero por casualidad pura está bien
// por default, sin protitipos, asume int como default, y justo se trabajaba con ints
#include <stdio.h>
#include "getnum.h"
#include <math.h>

int lugar (int num, int pos);

int
main( void )
{
	int boleto, esCapicua=1, cifras, aux;
	int  j;
	
	boleto = getint("Ingrese un número:");

	// quiero calcular las cifras

	for(cifras = 0, aux = boleto; aux > 0; aux /= 10, cifras++)


	for (j=1; j<=2; j++)
		if ( lugar(boleto, j) != lugar(boleto, cifras))
		{	
 			esCapicua=0;
			break;
		}
	printf("El boleto %ses capicúa",(esCapicua)? "":"no ");
	return 0;
}

int 
lugar( int num, int pos )
{	
	return (num / (int)pow(10, pos-1)) % 10;
}

// no completo, me dio fiaca pensar en cifras pares e impares
#include <stdio.h>

int
main(void)
{
	char letra;
	printf("\nIngrese una letra:");
	letra = getchar();
	if ( letra>='a' && letra <='z')
		printf("\n%c es una letra minúscula\n",letra);
	else
		if ( letra>='A' && letra<='Z')
			printf("\n%c es una letra mayúscula\n",letra);
	return 1;
}


// asumiendo que ejecuto esto después de compilar el código, (lo es)
// en este caso, pregunta al usuario un caracter y lo muestra en la teminal (lo que hace un cat)
// en el caso de > out.txt, pregunta al usuario un caracter y lo que devuelve el código lo manda al archivo out.txt
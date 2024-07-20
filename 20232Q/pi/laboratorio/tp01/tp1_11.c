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
	return 0;
}

//muere todo por agregar con #include al mismo archivo que se está compilando, lo saco

// tp1_11 < tp1_11.txt: con esto, agarra el contenido del .txt, que es a, y lo manda como argumento al ejecutable, el ejecutable devuelve por la terminal
// tp1_11 < tp1_11.txt > out.txt: con esto, manda el contenido del .txt, lo hace ejecutar al código, pero no devuelve por terinal, sino por el archivo out.txt
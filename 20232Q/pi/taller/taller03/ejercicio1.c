#include <stdio.h>
#include "getnum.h"

int main(void){

    int n, suma = 0;
    n = getint("Ingrese un número para sumar sus cifras: ");

    n = (n < 0) ? n * (-1) : n ; // si es negativo lo pasa a positivo

    while(n > 0){

        suma = suma + n % 10;
        n = n / 10;

    }

    printf("La suma de cifras es: %d\n", suma);

    return 0;
}


// otras soluciones posibles:

/*
do{
    C += aux % 10;
    aux /= 10;
}while (aux =! 0);
*/

/*
for(int sumaCifras, numero; numero>0; numero /= 10){
    resto = numero % 10;
    sumaCifras += resto;
}
*/





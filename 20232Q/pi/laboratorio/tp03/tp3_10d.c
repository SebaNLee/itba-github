#include <stdio.h>

int main(void){

int c, mayor;
mayor = 0;
while ( c = getchar() != EOF )
{
if ( c > mayor )
mayor = c;
}
printf ("El mayor es %c\n",mayor);
 



    return 0;
}


// falta paréntesis en la condición del while
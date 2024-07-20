#include <stdio.h>

int main(void){

int n = ... ;
switch (n)
{
case 1:
printf("El número es 1\n");
case 2:
printf("El número es 2\n"); 
break;
default:
printf("El número no es ni 1 ni 2\n");
break;
}




    return 0;
}

// falta un break en case 2
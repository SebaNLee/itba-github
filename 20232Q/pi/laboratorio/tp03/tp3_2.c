#include <stdio.h>

int main(void){

    int a, b;
    printf("Ingrese dos caracteres seguidos: ");
    a = getchar();
    b = getchar();

    if(a > b){
        printf("'%c' es mayor que '%c'\n", a, b);
    }
    else if(a < b){
        printf("'%c' es menor que'%c'\n", a, b);
    }
    else{
        printf("'%c' y '%c' son iguales\n", a, b);
    }

    return 0;
}
#include <stdio.h>
#define DIFERENCIA (('a') - ('A'))

int main(void){

    char n;

    printf("Ingrese un carácter en minúscula: ");
    n = getchar();

    printf("%c en mayúsculas: %c\n", n, (n >= 'a' && n <= 'z') ? n - DIFERENCIA : n);

    return 0;
}
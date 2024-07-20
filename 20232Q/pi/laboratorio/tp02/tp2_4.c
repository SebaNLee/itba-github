#include <stdio.h>

int main(void){

    printf("\a\n");
    printf("%c\n", 7);
    printf("%d\n", 7);
    printf("%f\n", 7.0);
    printf("%g\n", 7.0);

    return 0;

    //%f y %g son equivalentes en este caso, f es más preciso con el uso del *.* y g selecciona entre notación decimal y exponencial
}
#include <stdio.h>

#define PI 3.1415


int main(void){


    int a = 0;
    int b;
    b = PI + a++;

    printf("a: %d\n", a);
    printf("b: %d\n", b);

    return 0;
}

// supongo que va a terminar con a = 1 y b = 3
// agrego printf para corroborar

// correcto, b es int, entonces se trunca el valor de PI a 3 al ser guardado
#include <stdio.h>

void f1 (void){
    printf ("Dentro de la función f1\n");

    void f2(void){
        printf ("Dentro de f2\n");
    }
}

// no se puede definir una func dentro de otra
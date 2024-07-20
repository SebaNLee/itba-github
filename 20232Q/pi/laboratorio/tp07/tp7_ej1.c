
#include <stdlib.h>
#include <stdio.h>
int main(void) {

    char * p = malloc(10);

    for(int i=0; i < 10; i++) // se accede a +1 zonas de memoria con <=
        p[i] = i * 10;

    for(int i=0; i < 10; i++) // ídem
      printf("%d\n", p[i]);

    // hay que liberar la memoria
    free(p);

    return 0;
}


// a) funciona normalmente

// b) salta error de heap, pero es difícil ubicar dónde se da

// c) ahora en SUMMARY me dice en qué archivo y línea se da el error







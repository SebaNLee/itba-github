#include <stdio.h>

int main(void){

    int n;

    for(n = 3; n <= 23; n += 5){ // a
        printf("%d,", n);
    }
    printf("\n");

    for(n = 20; n >= -10; n -= 6){ // b
        printf("%d,", n);
    }
    printf("\n");

    for(n = 19; n <= 51; n += 8){ // c
        printf("%d,", n);
    }
        printf("\n");



    return 0;
}

// la coma de al final molesta, pero está hecho de igual forma en el solucionario


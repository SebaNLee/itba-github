#include <stdio.h>

#define DIMENSION 1024


int matriz[DIMENSION][DIMENSION];


int main(void) {
    printf("Hello World!\n");
    printf("%d\n", matriz[0][1]);
    return 0;
} 

// gcc -Wall -pedantic -std=c99 matriz.c -o matriz
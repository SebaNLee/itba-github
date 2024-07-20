#include <stdio.h>
#define DELTA 'O'

int main(void){
    int c, sum = 0;
    char shouldcount = 0;
    while (((c = getchar()) != '\n' && (c >= '0' && c <= '9')) || c == '.'){
        if(shouldcount == 1) sum+= c - DELTA;
        shouldcount += (c == '.');
    }
    printf("La suma es: %d\n", sum);
}
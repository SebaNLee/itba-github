#include <stdio.h>

#define DELTA ('a' - 'A')

int main(void){

    int c, cantUp = 0, cantLow = 0, min = 'z', flagUp = 0, flagCurrentMinUp = 0;
    while(((c = getchar()) >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' '){
        if(c != ' '){ // verifico que c no sea espacio, entonces es letra
            if((flagUp = (c >= 'A' && c <= 'Z'))){
                cantUp++;
                c += DELTA;
            }
            else{
                cantLow++;
            }
            if(c < min){
            min = c;
            flagCurrentMinUp = flagUp;
            }
        }
       
    }

    printf("\n#Mayus: %d | #Minus: %d\n", cantUp, cantLow);
    printf("El menor alfabéticamente es: %c\n", (flagCurrentMinUp) ? (min - DELTA) : min );

    return 0;
}
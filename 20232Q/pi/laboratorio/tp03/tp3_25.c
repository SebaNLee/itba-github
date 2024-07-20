#include <stdio.h>
#include "getnum.h"

int main(void){

    int num, cantUnos = 0;
    
    do{
        num = getint("Ingrese un número positivo: ");
    }while(num <= 0);

    while(num >= 1){
        
        if((num % 2) == 1){
            cantUnos++; // también se puede hacer con decalaje >>=
        }

        num /= 2;
    }

    printf("%d\n", cantUnos);

    return 0;
}




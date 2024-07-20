#include <stdio.h>

int main(void){

    int base;

    for(base = 1; ; base += 1){
        printf("%d, ", base * 10);
    }



    return 0;
}

// no termina de ejecutar más, hasta el infinito
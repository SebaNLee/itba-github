#include <stdio.h>

int main(void){

    int n;

    printf("%-10s%-10s%-10s%-10s\n\n", "N", "10*N", "100*N", "1000*N");

    for(n = 1; n <= 20; n++){
        printf("%-10d%-10d%-10d%-10d\n", n, n*10, n*100, n*1000);
    }

    return 0;
}
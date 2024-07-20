#include <stdio.h>

int main(void){
        
    int edad = 25;
    float longitud = 185.654;
    char letra = 'Z';


    printf("a) %-5d\n", edad);
    printf("b) %10d\n", edad);
    printf("c) %-10.2f\n", longitud);
    printf("d) %8d\n", letra);



    return 0;
}
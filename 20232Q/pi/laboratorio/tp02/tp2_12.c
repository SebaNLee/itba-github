#include <stdio.h>

int main(void){

    char carac;
    printf("Ingrese un carácter: ");
    carac = getchar();

    printf("El caracter %c %s es letra\n", carac, (((carac >= 'A' && carac <= 'Z') || (carac >= 'a' && carac <= 'z')) ? "SI" : "NO"));


    return 0;
}
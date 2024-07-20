#include <stdio.h>

int main(void){

    int a;
    printf("Ingrese una letra: ");
    a = getchar();

    if(a <= 'z' && a >= 'a'){
        printf("%c es una letra minúscula\n", a);
    }
    else if(a <= 'Z' && a >= 'A'){
        printf("%c es una letra mayúscula\n", a);
    }
    else{
        printf("%c no es una letra\n", a);
    }

    return 0;
}
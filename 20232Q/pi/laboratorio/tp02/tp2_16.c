#include <stdio.h>

int main(void){

    char car1, car2;
    printf("Ingrese dos caracteres seguidos: ");
    car1 = getchar();
    car2 = getchar();

    printf("El carácter %c es %s al carácter %c\n", car1, (car1 == car2) ? "IGUAL" : ((car1 > car2) ? "MAYOR" : "MENOR"), car2);

    return 0;
}
#include <stdio.h>
#define palabra "ultima prueba"

int main(void){

    int num1 = 53, num2 = 4;
    float num3 = 6.874;
    
    printf("num1= %10d\n", num1); // 10 dígitos (vacíos con espacio), sería    ...    53
    printf("num2= %010d\n", num2); // 10 dígitos (rellena con 0s), sería 0...04
    printf("num1= %-10d\n", num1); // 10 dígitos, pero a la izquierda 53 (... espacios)
    printf("num1+num2= %5i\n", num1 + num2); // 57 (con espacios adelante)
    printf("num1+num2= %5f\n", num1 + num2); // da error por formato (espera float pero da int), da cualqueir cosa
    printf("num3= %-3.2f\n", num3); // 6.87 a la izq 
    printf("num1= %-4d\nnum2= %-4d\nnum3= %3.1f\n", num1,num2, num3); // 53; 4; 6.9
    printf("num3(como entero)= %d\n", num3); // error, da cualquier cosa (va cambiando)
    printf("num1 / num2 = %d\n", num1 / num2); // 13
    printf("num2 / num1 = %d\n", num2 / num1); // 0
    printf("esta es la %s\n", palabra); // ultima prueba, reconoce como sting de chars

    return 0;
}

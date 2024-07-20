#include <stdio.h>

int main(void){

   // printf("%c", "3" + "4"); // inválido, ambos caracteres
    printf("%d\n", 3 + 4); // 7
    printf("%d\n", '3' + 4); // orden de 3 ascii + 4, da int 55
    printf("%d\n", '3' + '4'); // carácter orden de 3 + orden de 4, da int 103
    printf("%d\n", 3 + '4'); // 3 + orden de 4 ascii, da int 55
    printf("%s\n", "3" + '4'); // ??
    printf("%s\n", "3" + 4); // ??

    return 0;
}

// les agrego printf para ejecutarlos y ver qué dan

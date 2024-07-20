#include <stdio.h>

int main(void){

enum dias { LUN, MAR, MIE, JUE, VIE, SAB, DOM};
enum dias dia = LUN;
printf("Hoy es %d", dia);



    return 0;
}

// reemplazarlo por un swtich con case para todos los días
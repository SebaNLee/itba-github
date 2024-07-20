#include <stdio.h>

#define PI 3.14


int main(void){

    float a=0
    float b;
    
    b = a + PI++;
    
    return 0;
}

// error, porque a no tiene ;
// además, una constante no admite el operador de incremento ++
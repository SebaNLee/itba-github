#include <stdio.h>

int main(void){

float x = 1/3;
while(x != 0.52 )
x += .01;


    return 0;
}


// 1/3 es división entera
// no se debería comparar números reales con != o ==, por ser aproximaciones
// puede que nunca sea igual a 0.52, bucle infinito
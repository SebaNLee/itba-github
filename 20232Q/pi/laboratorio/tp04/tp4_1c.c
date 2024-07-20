#include <stdio.h>

#define CUBO(x) x * x * x

int cubo (int num);

int main(void){
    int a=4, b, c;
    b = CUBO(a+1); // con la macro quedaría: a+1*a+1*a+1, que no es lo que se quiere
    c = cubo(a+1);
    return 0;
}

int cubo ( int num ){
    return num * num * num;
}

// supongo, a = 4, b = 13, c =  125
// correcto, le faltan paréntesis a la macro

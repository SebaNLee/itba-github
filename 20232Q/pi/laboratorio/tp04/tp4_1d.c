#include <stdio.h>

#define CUBO(x) ((x) * (x) * (x))


int cubo (int num);

int main(void){

    int a=3, b, c, d, e, f;
    b = CUBO( ++a );
    c = CUBO( a++ );
    d = 3;
    e = cubo ( ++d );
    d = 3;
    f = cubo ( d++ );
    return 0;
}

int cubo ( int num ){
return num * num * num;
}

// supongo, a = 3, b, c, no se saben, por asociatividad
// d = 4, e = 64, f = 27 
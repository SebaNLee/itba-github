#include <math.h>
#include <errno.h>


int solucionCuadratica(int a, int b, int c, float *r1, float *r2){

    int cantReturn = 0;
    float x1, x2;

    errno = 0;

    x1 = (-b + sqrt(b * b - 4 * a * c)) / (2 * a);


    if(errno != EDOM && x1 == 0){
        *r1 = x1;
        cantReturn++;
    }


    errno = 0;

    x2 = (-b - sqrt(b * b - 4 * a * c)) / (2 * a);

    if(errno != EDOM && x2 == 0){
        *r2 = x2;
        cantReturn++;  
    }


    return cantReturn;
}

// algo está mal
// chequear soluciones en campus


#include <stdio.h>
#include <math.h>
#define INCREMENTO 0.001
#define EPSILON 0.0001


double funcion(double x); // devuelve la imagen


int main(void){

    // cambiar intervalo manualmente
    double izq = -4;
    double der = 4;
    int signoAnt, signoFun;

    signoAnt = (funcion(izq) >= 0) ? 1 : -1; // con esto guardo el signo inicial

    for(; izq <= der; izq += INCREMENTO){
        
        signoFun = (funcion(izq) >= 0) ? 1 : -1; 

        if(signoFun != signoAnt){ // me fijo si hubo cambio de signo
            printf("Raiz en %g\n", izq);
        }
        else if(fabs(funcion(izq)) < EPSILON){ // si se acerca al cero por EPSILON despreciable
            printf("Raiz en %g\n", izq);
        }

        signoAnt = signoFun;
    }

    return 0;
}


double funcion(double x){
    // cambiar función manualmente
    return cos(x);
}
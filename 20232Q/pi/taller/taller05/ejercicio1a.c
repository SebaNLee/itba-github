#include <stdio.h>
#include "random.h"

void mezlar(int original[], int dim){

    int aux, randNum;
    for(int i = 0; i < dim; i++){
        randNum = randInt(0, dim-1);
        aux = original[i];
        original[i] = original[randNum];
        original[randNum] = aux;
    }




    return ;
}

// copiado en la clase

// si se quiere lograr más mezcla, se podría pasar el ciclo varias veces
// pero con 1 vez así basta
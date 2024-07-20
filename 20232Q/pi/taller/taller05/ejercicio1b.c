#include <stdio.h>
#include "random.h"


void mezclar(int v[]){

    int aux;
    for(int i; v[i] != -1; ){
        aux = v[i];
        int rand = randInt(0, i);
        v[i] = v[rand];
        v[rand] = aux;
    }
}


// copiado en clase

/*
intento fallido

int main(void){

    arrDePrueba[] = {2, 4, 5, 2, 6, -1};
    printf("Original: %s\n", arrDePrueba[]);

    arrDePruebaMezclado[] = mezclar(arrDePrueba[]);

    printf("Mezclado: %s\n", arrDePruebaMezclado[]);
    return 0;
}



void mezclar(int original[]){

    int aux, randNum;

    while(original[i] != -1){

        randNum = randInt(0, dim-2); // ahora sería -2 para no alterar el último valor (-1)
        aux = original[i];
        original[i] = original[randNum];
        original[randNum] = aux;

        i++;
    }

    return;
}




*/



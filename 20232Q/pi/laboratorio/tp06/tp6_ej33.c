

#include "random.h"
#include <ctype.h>






void mezclarAlfabeto(char alfa[]){

    int aux, rand;

    for(int i = 0; i < MAX; i++){

        rand = randInt(0, MAX);

        aux = alfa[i];
        alfa[i] = alfa[rand];
        alfa[rand] = aux;
    }

    return;
}


void crearAlfabeto(char alfa[]){

    for(int i = 0; i < MAX; i++){
        alfa[i] = 'A' + i;
    }

    mezclarAlfabeto(alfa);

    return;
}


void codificar(char *texto, char alfa[], char *salida){

    while(*texto){
        if(isalpha(*fuente)){
            *salida++ = alfa[toupper(*texto) - 'A']
        }
        else{
            *salida++ = *texto;
        }
    }

    *salida = 0;
    
    return;
}


void decodificar(){
// se hace por posición

}
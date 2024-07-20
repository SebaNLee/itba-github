
#include <stdio.h>
#include "getnum.h"

#define MAX_LETRAS 50


void cargar(tFactura f, unsigned total){


    for(int i = 0; i < total; i++){

        leerItem(f[i], i);

    }

    return;
}


tItem leerItem(tItem * item, int i){

    printf("Ingrese los datos del item %d\n", i)

    tItem->codigo = getint("Ingrese el código el del item: ");

    fgets(tItem->desc, MAX_LETRAS, STDIO);

    tItem->precio = getfloat("Ingrese el precio del item: ");

}

// creo que está mal, leerItem no tiene que recibir parámetros




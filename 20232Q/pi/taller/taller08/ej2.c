
#include <stdlib.h>
#include <stdio.h>
#include "getnum.h"


// devuelve 1 si se repite, 0 si no se repite
int seRepite(int arr[], int valorActual, int dim){

    for(int i = 0; i < dim; i++){
        if(arr[i++] == valorActual){
            return 1;
        }
    }

    return 0;
}


int * cargarNumeros(int * dim){

    int *arr = malloc(1 * sizeof(int));
    int i = 0, n;
    do{
        n = getint("Ingrese un número: ");
        arr[i++] = n;
        arr = realloc(arr, (i + 1) * sizeof(int));

    }while(!seRepite(arr, n, i));

    arr = realloc(arr, i * sizeof(int));

    *dim = i;

    return arr;
}
// esto se podría hacer más eficiente si se definen y se suma memorias en incrementos bloques 
// con esto, pediría menos veces memoria, por ejemplo BLOQUE 5


int main(void){

    int dim;
    int *arr;
    arr = cargarNumeros(&dim);
    
    for(int i=0; i<dim; i++)
        printf("%d \n", arr[i]);


    free(arr);
    return 0;
}







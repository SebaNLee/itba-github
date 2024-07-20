#include <stdio.h>
#include "random.h"


// agrego un main para probarlo

void desordenar(int arr[], int dim);

int main(){


    int arr[] = {3, 4, 2, 5, 6, 7, 8, 9};
    int dim = 8;

    desordenar(arr, dim);

    for(int i = 0; i < dim; i++){
        printf("%d ", arr[i]);
    }
    printf("\n");

    return 0;

}


void desordenar(int arr[], int dim){

    int aux, rand;

    for(int i = 0; i < dim; i++){

        rand = randInt(0, dim - 1);

        aux = arr[i];
        arr[i] = arr[rand];
        arr[rand] = aux;
    }

    return;

}












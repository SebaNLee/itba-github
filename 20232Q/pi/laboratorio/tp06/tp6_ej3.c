#include <math.h>


int maxDiferencia(const int arr[]){

    double mayorDif = 0;

    for(int i = 0; arr[i] != 0; i++){
        if(fabs(arr[i + 1] - arr[i]) > mayorDif){
            mayorDif = fabs(arr[i + 1] - arr[i]);
        }
    }



    return mayorDif;
}


// no me anda algo por el EPSILON, tal vez porque falta



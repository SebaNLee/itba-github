#include <stdio.h>

int dondeEsta(v[], int dim, int num, int pos[]){ // pos[] entra con basura, pero sale con las posiciones correctas

    int i = 0, j = 0;

    while(i<dim && v[i] =< num){
        if(v[i] == num){
            pos[j++] = i;
            i++;
        } 
    }
    return j; // !!
}

// copiado de clase

/* intento fallido
void dondeEsta(v[], int dim, int num){

    int i, encontrados;

    for(i = 0; i < dim; i++){

        if(v[i] == num){
            encontrados++;
        }


    }



    return;
}
*/
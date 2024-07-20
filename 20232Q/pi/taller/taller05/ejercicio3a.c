#include <stdio.h>

int interseccion(int v1[],int  v2[],int dim1,int dim2,int inter[]){

    int i, j, k = 0;

    for(i = 0; i < dim1; i++){
        for(j = 0; j < dim2; j++){
            if(v1[i] == v2[j]){
                inter[k++] = v1[i];
                break;
            }
        }
    }

    return k;
}

// algo está mal con esto, revisar
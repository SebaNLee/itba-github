

int esRepetido(int newVector[], int dim, int elemento){

    for(int j = 0; j < dim; j++){
        if(newVector[j] == elemento){
            return 1;
        }
    }

    return 0;

}


int eliminaRepetidos(const int vector[], int dim, int newVector[]){

    int newDim = 0;

    for(int i = 0; i < dim; i++){

        if(!esRepetido(newVector, newDim, vector[i])){
            newVector[newDim++] = vector[i];
        }
    }

    return newDim;
}



/*
está mal, porque en el if no chequeo todos los valores al mismo tiempo, no se puede
int eliminaRepetidos(const int vector[], int dim, int newVector[]){

    int i, j, newDim = 0;
    

    for(i = 0; i < dim; i++){

        for(j = 0; j <= newDim; j++){


            if(vector[i] != newVector[j]){

                newVector[newDim++] = vector[i];

                break;
            }



        }


    }

    return newDim;
}
*/

















void secuenciaAsc(int vector[], int dim, int *pComienzo, int *pLongitud){

    int i, comienzoAux = 0, longitudAux = 1;
    *pComienzo = 0;
    *pLongitud = dim > 0;

    for(i = 1; i < dim; i++){

        if(vector[i] > vector[i - 1]){
            longitudAux++;
        }
        else{
            comienzoAux = i;
            longitudAux = 1;
        }
        if(longitudAux > *pLongitud){
            *pComienzo = comienzoAux;
            *pLongitud = longitudAux;
        }
    }


    return;
}







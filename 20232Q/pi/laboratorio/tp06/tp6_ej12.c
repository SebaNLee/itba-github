#define DIM_MAX 10

void traspuesta(int matriz[][DIM_MAX], int dim){

    int aux, i, j;

    for(i = 0; i < dim; i++){

        for(j = 0; j < dim; j++){

            aux = matriz[i][j];
            matriz[i][j] = matriz[j][i];
            matriz[j][i] = aux;
        }
    }

    return;
}


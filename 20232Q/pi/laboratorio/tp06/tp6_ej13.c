#define DIM_MAX 10


int productoij(int m1[], int m2[][DIM_MAX], int col, int dim){

    int producto = 0;

    for(int i = 0; i < dim; i++){

        producto += m1[i] * m2[col][i];

    }


    return producto;
}



void productoMat(int m1[][DIM_MAX], int m2[][DIM_MAX], int resultado[][DIM_MAX], int dim){

    int i, j;

    for(i = 0; i < dim; i++){

        for(j = 0; j < dim; j++){

            resultado[i][j] = productoij(m1[i], m2, j, dim);
        }
    }

    return;
}




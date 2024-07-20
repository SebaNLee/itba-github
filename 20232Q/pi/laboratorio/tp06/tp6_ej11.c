
#define MAX 20


void swap(int v1[], int v2[], int col){

    for(int i = 0; i < col; i++){

        int aux = v1[i];
        v1[i] = v2[i];
        v2[i] = aux;

        return;
    }



}




void ordenaMatriz(int matriz[][MAX], int fil, int col, int colOrd){

    for(int i = 0; i < fil - 1; i++){

        for(int j = i + 1; j < fil; j++){

            int menor = i;

            if(matriz[j][colOrd - 1] < matriz[menor][colOrd - 1]){

                menor = j;


            }

            if(i != menor){
                swap(matriz[i], matriz[menor], col);
            }

        }
    }

    return;
}












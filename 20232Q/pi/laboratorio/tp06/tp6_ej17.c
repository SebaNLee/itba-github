// copio el archivo de prueba


#include <stdio.h>
#include <assert.h>

#define N 4
#define M 5


int armaFilas(int mat[][M], int vec[]);
int armaVector(int filMat[], int *vec);


int main(void)
{
     int m1[N][M] = { { 1,2,3,4,5},
                { 1,2,13,4,5},
                { 1,2,4,4,5},
                { 1,2,-3,4,5}};
    int vec[N];
    int n = armaFilas(m1, vec);
    assert(n==2);
    printf("%d\n", vec[0]);
    assert(vec[0]==12345);
    assert(vec[1]==12445);

    int m2[N][M] = { { 1,0,3,4,5},
                { 0,2,1,4,5},
                { 9,9,9,9,9},
                { 0,0,1,4,5}};
     n = armaFilas(m2, vec);
    assert(n==4);
    assert(vec[0]==10345);
    assert(vec[1]==2145);
    assert(vec[2]==99999);
    assert(vec[3]==145);


    int m3[N][M] = { { 11,0,3,4,5},
                { 0,2,1,14,5},
                { 9,9,9,91,9},
                { 0,0,1,14,5}};
     n = armaFilas(m3, vec);
    assert(n==0);
    

    printf("OK!\n");
    return 0;
}



int armaFilas(int mat[][M], int vec[]){

    int dimVec = 0, i;

    for(i = 0; i < N; i++){
        if(armaVector(mat[i], &vec[dimVec])){ // si se arma un vector se devuelve 1 y 0 si no lo arma
            dimVec++;
        }
    }

    return dimVec;
}

int armaVector(int filMat[], int *vec){ // posición del vec como variable

    int i = 0, aux = 0;

    while(i < M){

        if(filMat[i] < 0 || filMat[i] > 9){
            return 0;
        }

        aux = 10 * aux + filMat[i];
        i++;
    }

    *vec = aux;

    return 1;
}

// lo que estaba haciendo mal era en L59, estaba pasando el vector como un valor
// entonces todo lo que hacía en la func armaVector no se guardaba
// esto se soluciona al pasar la dirección de memoria del vecto con & y recibirlo en la func como puntero *







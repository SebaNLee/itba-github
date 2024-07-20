// copio test

#include <stdio.h>
#include <assert.h>

#define COLS 4

int sonAmigas(const int (m1[])[COLS], unsigned int fils1,const int (m2[])[COLS], unsigned int fils2 );
int filaPerteneceEnFila(const int v[], const int vCheck[]);
int filaPertenece(const int v[], unsigned int fils1, const int m2[][COLS], unsigned int fils2);
int esAmiga(const int m1[][COLS], unsigned int fils1, const int m2[][COLS], unsigned int fils2);



int 
main(void)
{
  int m1[][COLS] = {{0,1,3,2}, {4,6,5,6}, {9,8,7,9}};
  int m2[][COLS] = {{0,1,2,3}, {-3,9,8,7}, {-1,3,4,7}, {4,5,6,8}};
  int m3[][COLS] = {{2,3,3,7}};
  assert(sonAmigas(m1,3,m2,4)==1);
  assert(sonAmigas(m2,4,m1,3)==2);
  assert(sonAmigas(m1,3,m3,1)==0);
  assert(sonAmigas(m3,1,m1,3)==0);
  int res = sonAmigas(m1,3,m1,3);
  assert(res==1 || res==2);

  return 0;
}


// devuelve 1 si fila pertenece en fila, de lo contrario devuelve 0
int filaPerteneceEnFila(const int v[], const int vCheck[]){

    for(int i = 0, j = 0; i < COLS, j < COLS; ){

        if(v[i] > vCheck[j]){
            j++;
        }
        else if(v[i] == vCheck[j]){
            i++;
        }
        else{
            return 0;   
        }

        return i == COLS;
    }


}

// devuelve 1 si la fila pertenece , devuelve 0 si no pertenece 
int filaPertenece(const int v[], unsigned int fils1, const int m2[][COLS], unsigned int fils2){


    for(int i = 0; i < COLS; i++){

        if(filaPerteneceEnFila(v, m2[i])){
            return 1;
        }
    }

    return 0;
}

// devuelve 1 si m1 pertenece a m2, devuelve 0 si no pertenece
int esAmiga(const int m1[][COLS], unsigned int fils1, const int m2[][COLS], unsigned int fils2){

    for(int i = 0; i < fils1; i++){
        if(!filaPertenece(m1[i] , fils1, m2, fils2)){
            return 0; // devuelve 0 si fila no pertenece a matriz
        }
    }
    return 1; // devuelve 1 si todos pertenecen
}


int sonAmigas(const int (m1[])[COLS], unsigned int fils1, const int (m2[])[COLS], unsigned int fils2){

    if(esAmiga(m1, fils1, m2, fils2)){ // chequeo si el primero es amiga del segundo
        return 1;
    }
    if(esAmiga(m2, fils2, m1, fils1)){ // chequeo si el segundo es amiga del primero
        return 2;
    }
    return 0;

}


// me daba segmentation fault, copio el código de prueba acá
// solucionado: era que el prototipo de la prueba no coincidía con lo que programé (orden)

//
//


#include <stdio.h>
#include <assert.h>

int contiene(const int v1[], unsigned int dim1, const int v2[], unsigned int dim2);

int main(void) {

  int v1[] = {1,6,5,3,2};
  int v2[] = {1,2};
  assert(contiene(v1, 5, v2, 2)==2);
  assert(contiene(v2, 2, v1, 5)==1);
  assert(contiene(v1, 1, v2, 2)==1);
  assert(contiene(v1, 0, v2, 2)==1);
  assert(contiene(v1, 5, v2, 0)==2);
  
  int v3[] = {1,2,3,4,5,6};
  assert(contiene(v1, 5, v3, 6)==1);

  int cnt = contiene(v1, 0, v3, 0);
  assert(cnt ==1 || cnt==2);

  int v4[] = { 10, 20, 30, 1, 2};
  assert(contiene(v2, 2, v4, 4)==0);
  assert(contiene(v2, 2, v4, 5)==1);
  assert(contiene(v4, 5, v2, 2)==2);

  int v5[] = {1,1,1,1,1,2,1,2};
  int v6[] = {1,2,3};
  assert(contiene(v5, 8, v6, 3)==1);
  assert(contiene(v6, 3, v5, 8 )==2);

  printf("OK!\n");
  return 0;
}


//
//


// se fija si un elem se encuentra en un vector
int encuentro(int elem, const int v[], int dim){

    for(int i = 0; i < dim; i++){
        if(elem == v[i]){
            return 1;
        }
    }

    return 0;
}


// se fija si el primer vector pertenece al segundo vector
int pertenece(const int v1[], unsigned int dim1, const int v2[], unsigned int dim2){
    int i, retorno;

    for(i = 0, retorno = 1; (i < dim1) && retorno; i++){
        retorno = encuentro(v1[i], v2, dim2);
    }


    return retorno;
}


int contiene(const int v1[], unsigned int dim1, const int v2[], unsigned int dim2){

    if(pertenece(v1, dim1, v2, dim2)){
        return 1;
    }
    if(pertenece(v2, dim2, v1, dim1)){
        return 2; 
    }

    return 0;
}


/*
está mal, porque no chequeo sumo de más si hay algún elemento repetido


int contiene(int v1[], int v2[], int dim1, int dim2){
    
    int i, j, respuesta = 0, cantComp = 0;

    for(i = 0; i < dim1 && cantComp != dim1; i++){

        for(j = 0; j < dim2 && cantComp != dim2; j++){

            if(v1[i] == v2[j]){

                cantComp++;
                break;
            }

        }
    }

    if(cantComp == dim1){
        respuesta = 1;
    }
    else if(cantComp == dim2){
        respuesta = 2;
    }


    return respuesta;
}


*/










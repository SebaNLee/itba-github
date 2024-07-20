// copio test

#include <stdio.h>
#include <assert.h>

#define DIM 9

int sudokuSolver(char m[][DIM]);
int checkCols(char m[][DIM], int j);
int checkFils(char m[]);
int checkSubCuadrado(char m[][DIM], int fil, int col);

int main(void) {
  // Una matriz vacía no es solución
  char sudoku[DIM][DIM] = {{0}};

  assert(sudokuSolver(sudoku)==0);

  char sudoku2[DIM][DIM] = {
        {3,8,1,9,7,6,5,4,2}, 
        {2,4,7,5,3,8,1,9,6},
        {5,6,9,2,1,4,8,7,3},
        {6,7,4,8,5,2,3,1,9},
        {1,3,5,7,4,9,6,2,8},
        {9,2,8,1,6,3,7,5,4},
        {4,1,2,6,8,5,9,3,7},
        {7,9,6,3,2,1,4,8,5},
        {8,5,3,4,9,7,2,6,1}
  };

  assert(sudokuSolver(sudoku2)==1);
  
  char sudoku3[DIM][DIM] = {
        {2,8,1,9,7,6,5,4,2}, 
        {3,4,7,5,3,8,1,9,6},
        {5,6,9,2,1,4,8,7,3},
        {6,7,4,8,5,2,3,1,9},
        {1,3,5,7,4,9,6,2,8},
        {9,2,8,1,6,3,7,5,4},
        {4,1,2,6,8,5,9,3,7},
        {7,9,6,3,2,1,4,8,5},
        {8,5,3,4,9,7,2,6,1}
  };

  assert(sudokuSolver(sudoku3)==0);
  
  // Cuando se tomó este ejercicio en un parcial, en una de las respuestas
  // sólo chequeaban que la suma de cada fila, columna y cuadrado fuera 45
  char sudoku4[DIM][DIM] = {
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}, 
        {5,5,5,5,5,5,5,5,5}
  };

  assert(sudokuSolver(sudoku4)==0);

  char sudoku5[DIM][DIM] = {
        {3,8,1,9,7,6,5,4,12}, 
        {2,4,7,5,3,8,1,9,6},
        {5,6,9,2,1,4,8,7,3},
        {6,7,4,8,5,2,3,1,9},
        {1,3,5,7,4,9,6,2,8},
        {9,2,8,1,6,3,7,5,4},
        {4,1,2,6,8,5,9,3,7},
        {7,9,6,3,2,1,4,8,5},
        {8,5,3,4,9,7,2,6,1}
  };

  assert(sudokuSolver(sudoku5)==0);

  return 0;
}


// 




// devuelve 1 si valida columnas, 0 si no es correcto
int checkCols(char m[][DIM], int j){

    char aux[DIM] = {0};
    int indice;

    for(int i = 0; i < DIM; i++){
        indice = m[i][j];

        if(aux[indice - 1] == 1){
            return 0;
        }
        else{
            aux[indice - 1]++;
        }
    }
    return 1;
}


// devuelve 1 si valida filas, 0 si no es correcto
int checkFils(char m[]){

    char aux[DIM] = {0};
    int ok = 1, indice;

    for(int i = 0; i < DIM && ok; i++){
        indice = m[i];

        if(aux[indice - 1] == 0){
            aux[indice - 1]++;
        }
        else{
            return ok;
        }
    }
    return ok;
}


// retorna 1 si es válida, 0 si no es correcto
int checkSubCuadrado(char m[][DIM], int fil, int col){
    char aux[DIM] = {0};
    int indice;

    for(int i = fil; i < fil + 3; i++){
        for(int j = col; j < col + 3; j++){
            indice = m[i][j];
            if(aux[indice - 1] == 0){
                aux[indice - 1]++;
            }
            else{
                return 0;
            }
        }
    }

    return 1;
}


int sudokuSolver(char m[][DIM]){

    int ok = 1; // flag se prende mientras cumpla condiciones

    for(int i = 0; i < DIM && ok; i++){
        ok = checkFils(m[i]);
    }

        for(int j = 0; j < DIM && ok; j++){
        ok = checkCols(m, j);
    }

    if(ok){
        for(int fil = 0; fil < DIM && ok; fil += 3){
            for(int col = 0; col < DIM && ok; col += 3){
                ok = checkSubCuadrado(m, fil, col);
            }
        }
    }

    return ok;
}




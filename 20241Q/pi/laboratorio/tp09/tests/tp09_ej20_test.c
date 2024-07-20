#include <stdio.h>
#include <assert.h>

#define DIM 9


int sudokuSolver(char matriz[DIM][DIM]);

int main(void) {
  // Una vacia tiene que tener solucion
  char sudoku[DIM][DIM] = {{0}};

  assert(sudokuSolver(sudoku)==1);
  for(int i=0; i < DIM; i++) {
    for(int j=0; j < DIM; j++) {
      printf("%1d ", sudoku[i][j]);
    }
    putchar('\n');
  }

  printf("\nUno sin solucion\n");
  char sudoku2[DIM][DIM] = {{1,2,3,4,5,6,7,8,9},
                           {5,6,7,8,9,1,2,3,4},
                           {8,9,1,2,3,7,5,6,0},
                          };

  assert(sudokuSolver(sudoku2)==0);
  for(int i=0; i < DIM; i++) {
    for(int j=0; j < DIM; j++) {
      printf("%1d ", sudoku2[i][j]);
    }
    putchar('\n');
  }

  printf("\nUno con solucion pero con celdas no vacias\n");
  char sudoku3[DIM][DIM] = {
                          {0,2,0,3,0,0,0,0,0},
                          {9,4,0,6,8,7,0,0,0},
                          {1,6,0,0,0,0,0,0,0},
                          {0,0,0,0,9,2,5,4,0},
                          {0,0,0,0,6,0,0,0,0},
                          {8,0,0,4,0,3,0,1,6},
                          {0,1,0,0,7,6,0,8,2},
                          {0,0,0,9,0,0,6,0,0},
                          {7,0,6,0,0,4,9,3,0}
                          };

  assert(sudokuSolver(sudoku3)==1);
  for(int i=0; i < DIM; i++) {
    for(int j=0; j < DIM; j++) {
      printf("%1d ", sudoku3[i][j]);
    }
    putchar('\n');
  }

  return 0;
}
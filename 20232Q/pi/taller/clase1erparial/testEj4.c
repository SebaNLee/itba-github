#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#define COLS 5
#define ERROR 0
#define EXITO 1

int eliminaLetras(char s[], int matriz[][COLS], int filas, int cols);
int
main(){
    int M[6][COLS];
    char s[]="lAs buenas Ideas Escasean";
    assert(eliminaLetras(s,M,6,5)==EXITO);
    assert(strcmp(s,"ls uns Is ssn")==0);
    assert(M[0][0] == 1 && M[0][1] == 4 && M[0][2] == 19 && M[0][3] == 12 && M[0][4] == 6);
    assert(M[1][0] == 8 && M[1][1] == -1 && M[1][2] == -1 && M[1][3] == -1 && M[1][4] == 13);
    assert(M[2][0] == 14 && M[2][4] == 17);
    assert(M[3][0] == 20 && M[3][4] == 22);
    assert(M[4][0] == 23 && M[4][4] == -1);
    assert(M[5][0] == -1);

    int M2[5][COLS];
    char s2[]="lAs buenas Ideas Escasean";
    assert(eliminaLetras(s2,M2,5,4)==ERROR);
    assert(strcmp(s2,"ls uens Ies Essean")==0);
    assert(M2[0][0] == 1 && M2[0][1] == 4 && M2[0][2] == 19 && M2[0][3] == 12);
    assert(M2[1][0] == 8 && M2[1][1] == -1 && M2[1][2] == -1 && M2[1][3] == -1);
    assert(M2[2][0] == 14);
    assert(M2[3][0] == 20);
    assert(M2[4][0] == -1);
    printf("OK!\n");
    return EXIT_SUCCESS;
}
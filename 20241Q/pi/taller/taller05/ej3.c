#include <stdio.h>

#define CANT_DIR 8

#define FILAS 7
#define COLS 6

#define ERROR (-1)

typedef enum {E = 0, NE, N, NO, O, SO, S, SE} Dir; // cada índice es +45

int calculaDireccion(char mat[][COLS], int alto, int ancho, int x, int y)
{
    // verifico si jugador dentro del tablero
    if(x < 0 || x >= alto || y < 0 || y >= ancho)
    {
        return ERROR;
    }


    int max = 0, returnValue = -1;

    // me armo una matriz que me indica los incrementos por dirección
    int matrizDir[CANT_DIR][2] = {{0,1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}};

    int flagComplete, count;

    for(int i = 0; i < CANT_DIR; i++)
    {
        flagComplete = 0;
        count = 0;

        for(int j = x, k = y; j < alto && k < ancho && j >= 0 && k >= 0 && !flagComplete; j = j + matrizDir[i][0], k = k + matrizDir[i][1])
        {
            if(mat[j][k] == 'X' || mat[j][k] == '1')
            {
                flagComplete = 1;
            }
            count++;
        }

        if(count > max)
        {
            max = count;
            returnValue = i;
        }
    }


    returnValue = returnValue * 45;

    return returnValue;
}



// gcc ej3.c ej3_test.c -o ej3 -Wall -pedantic -std=c99
/*
Escribir la funciÃ³n posiciÃ³nFinal que recibe:
- Tablero: es una matriz de FILAS filas y COLS columnas, donde FILAS y COLUMNAS son
constantes simbÃ³licas previamente definidas
- PosiciÃ³n inicial de una bola en el tablero: (xi, yi), ambos parÃ¡metros enteros
- DirecciÃ³n de movimiento (dirx, diry) ambos parÃ¡metros enteros
- Pasos (n), donde n es entero
-
La funciÃ³n deberÃ¡ retornar en dos variables (xf, yf), de tipo entero, la posiciÃ³n final de la bola en el tablero
si se mueve n pasos a partir de (xi, yi) en la direcciÃ³n indicada por (dirx,diry), actualizando el tablero con
una â€˜Bâ€™ en la posiciÃ³n final. Hay obstÃ¡culos en el tablero seÃ±alados con una â€˜Xâ€™.
La direcciÃ³n indicada (dirx, diry) puede ser alguna de estas:
(0,1)
(0,-1)
(1,1)
(1,-1)
(-1,1)
(-1,-1)
(1,0)
(-1,0)

Al efectuar n pasos, si llega a un borde deberÃ¡ detenerse allÃ­.
La funciÃ³n devolverÃ¡ EXITO si pudo completar los n pasos en la direcciÃ³n indicada.
La funciÃ³n devolverÃ¡ ERRORPASOS si no puede completar los n pasos en la direcciÃ³n indicada.
La funciÃ³n devolverÃ¡ ERRORPARAMETROS si hay error en los argumentos de la funciÃ³n.
*/

#include <stdio.h>
#include <assert.h>

#define FILAS 8
#define COLUMNAS 8
#define EXITO 1
#define ERRORPASOS -1
#define ERRORPARAMETROS -2

char posicionFinal(char tablero[FILAS][COLUMNAS], int xi, int yi, int dirx, int diry, int n, int *xf, int *yf);


int main()
{
  char tablero[FILAS][COLUMNAS] = {
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
      {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

  int xi, yi, dirx, diry, n, xf, yf;
  xi = yi = 3;
  dirx = diry = 1;
  n = 3;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == EXITO);
  assert(xf == 6 && yf == 6);
  assert(tablero[xf][yf] == 'B');

  dirx = 0;
  diry = 1;
  n = 4;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPASOS);
  assert(xf == 3 && yf == 6);
  assert(tablero[xf][yf] == 'B');

  n = 5;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPASOS);
  assert(xf == 3 && yf == 6);
  assert(tablero[xf][yf] == 'B');

  dirx = 1;
  diry = 0;
  n = 4;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPASOS);
  assert(xf == 6 && yf == 3);
  assert(tablero[xf][yf] == 'B');

  dirx = diry = -1;
  n = 20;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPASOS);
  assert(xf == 0 && yf == 0);
  assert(tablero[xf][yf] == 'B');

  dirx = diry = 0;
  n = 3;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPARAMETROS);

  dirx = diry = 2;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPARAMETROS);

  dirx = -1;
  diry = -2;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPARAMETROS);

  dirx = 0;
  diry = -1;
  n = -2;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPARAMETROS);

  xi = yi = -1;
  n = 1;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPARAMETROS);

  xi = yi = 20;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPARAMETROS);

  xi = yi = 0;
  assert(posicionFinal(tablero, xi, yi, dirx, diry, n, &xf, &yf) == ERRORPASOS);
  assert(xf == 0 && yf == 0);
  assert(tablero[xf][yf] == 'B');

  puts("OK");
  return 0;
}
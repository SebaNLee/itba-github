#include <stdio.h>
#include <limits.h>

int
main(void) {
   int c = INT_MAX;
   int d;
   d = c + c;
   int e = c + 1;

   printf("c=%d   d=%d   e=%d\n", c, d, e);
   return 0;
}


// a) c: máximo valor de int del OS. d: es como multiplicar todo, full 1s y un 0 al final. e: el mínimo valor de int en OS
// b) correcto
// c) da error, es un flag que produce errores en caso de overflow (vars d y e)

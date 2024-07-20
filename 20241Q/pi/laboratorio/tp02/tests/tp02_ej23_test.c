#include <stdio.h>
#define EPSILON 0.00001

// Verifica si ambos números son iguales en base a un EPSILON
int approx(double x, double y);

// Más adelante veremos el uso de assert para escribir programas de testeo
int main(void) {
  if ( approx(0.01, 0.02) ) {
    puts("Falla para 0.01 y 0.02");
  }

  if ( approx(0.01, -0.01) ) {
    puts("Falla para 0.01 y -0.01");
  }

  if ( ! approx(0.01, 0.01) ) {
    puts("Falla para 0.01 y 0.01");
  }

  if ( !approx(0.00001, 0.000015) ) {
    puts("Falla para 0.00001 y 0.000015");
  }

  if ( !approx(0.00001, 0.000019999) ) {
    puts("Falla para 0.00001 y 0.000019999");
  }

  if ( !approx(0.00009, 0.000099999) ) {
    puts("Falla para 0.00001 y 0.000019999");
  }

  if ( !approx(0.00001, 0.00001) ) {
    puts("Falla para 0.00001 y 0.00001");
  }

  if ( !approx(0.0, 0.0) ) {
    puts("Falla para 0.0 y 0.0");
  }

  puts("OK");
  return 0;
}
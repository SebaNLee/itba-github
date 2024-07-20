#define EPSILON 0.000001
#include <math.h>
#include <assert.h>
#include <stdio.h>

/* Calcula la maxima diferencia entre dos valores consecutivos de un arreglo de reales */
double
maxDiferencia(const double arreglo[]);

int main(void) {

  double v[] = {1.0, 0.0};
  assert(fabs(maxDiferencia(v))==0.0);

  double v2[] = {1.0, 1.0, 1.0, 0.0};
  assert(fabs(maxDiferencia(v2))==0.0);

  double v3[] = {1.5, 3.0, -1.0, 0.0};
  assert(fabs(maxDiferencia(v3) - 4.0) < EPSILON);

  double v4[] = {1.5, 3.0, 4.5, 3.0, 0.0};
  assert(fabs(maxDiferencia(v4) -1.5) < EPSILON);

  double v5[] = {2.5, 1.0, 1.5, 2.0, 0.0};
  assert(fabs(maxDiferencia(v5)- 1.5) < EPSILON );

  double v6[] = {1.5,  -1.0, 3.0, 0.0};
  assert(fabs(maxDiferencia(v6) - 4.0) < EPSILON);

  puts("OK!");
  return 0;
}
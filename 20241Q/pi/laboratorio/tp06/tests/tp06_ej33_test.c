#include <string.h>
#include <assert.h>
#include <stdio.h>

#define MAX 100

int main(void) {
  char msg[MAX];
  char dest[MAX];
  // Debemos probar con mayusculas, porque siempre decodifica a mayuscuas
  char *s = "MENSAJE A CODIFICAR";
  char *s2 = "Mensaje a codificar";
  char alpha[MAX];
  crearAlfabeto(alpha);
  codificar(s, alpha, msg);
  assert(strlen(s)==strlen(msg));
  assert(strcmp(s, msg) != 0);
  decodificar(msg, alpha, dest);
  assert(strcmp(dest, s) == 0);

  codificar(s2, alpha, msg);
  assert(strlen(s2)==strlen(msg));
  assert(strcmp(s2, msg) != 0);
  decodificar(msg, alpha, dest);
  assert(strcmp(dest, s) == 0);
  
  puts("OK");
  return 0;
}
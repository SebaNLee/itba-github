#include <stdio.h>
#include <assert.h>
#include <string.h>

int elimina(char s[]);

int main() {
  char s[] = "lAs buenas Ideas Escasean";
  char s_res[] = "lAsbuenasIdeasEscasean";
  assert(elimina(s) == strlen(s_res));
  assert(strcmp(s, s_res) == 0);

  char s2[] = "";
  assert(elimina(s2) == 0);
  assert(strcmp(s2, "") == 0);

  char s3[] = "1234abc..**--+1asd33";
  char s3_res[] = "abcasd";
  assert(elimina(s3) == strlen(s3_res));
  assert(strcmp(s3, s3_res) == 0);

  char s4[] = "1234";
  assert(elimina(s4) == 0);
  assert(strcmp(s4, "") == 0);

  puts("OK!");
  return 0;
}#include <stdio.h>
#include <assert.h>
#include <string.h>

int elimina(char s[]);

int main() {
  char s[] = "lAs buenas Ideas Escasean";
  char s_res[] = "lAsbuenasIdeasEscasean";
  assert(elimina(s) == strlen(s_res));
  assert(strcmp(s, s_res) == 0);

  char s2[] = "";
  assert(elimina(s2) == 0);
  assert(strcmp(s2, "") == 0);

  char s3[] = "1234abc..**--+1asd33";
  char s3_res[] = "abcasd";
  assert(elimina(s3) == strlen(s3_res));
  assert(strcmp(s3, s3_res) == 0);

  char s4[] = "1234";
  assert(elimina(s4) == 0);
  assert(strcmp(s4, "") == 0);

  puts("OK!");
  return 0;
}
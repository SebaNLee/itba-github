#include <string.h>
#include <assert.h>
#include <stdio.h>

int main(void) {
  char s[100];

  assert(normalizar("1.1.1", s)==0);
  assert(normalizar("a", s)==0);
  assert(normalizar("1.a11", s)==0);
  assert(normalizar("1-1", s)==0);

  assert(normalizar("1", s));
  assert(strcmp(s, "1E+00")==0);
  assert(normalizar("0012", s));
  assert(strcmp(s, "1.2E+01")==0);

  assert(normalizar("145.720", s));
  assert(strcmp(s, "1.4572E+02")==0);
  assert(normalizar("134597", s));
  assert(strcmp(s, "1.34597E+05")==0);

  assert(normalizar("0.34", s));
  assert(strcmp(s, "3.4E-01")==0);
  assert(normalizar("0.00200", s));
  assert(strcmp(s, "2E-03")==0);
  assert(normalizar("987654321011", s));
  assert(strcmp(s, "9.87654321011E+11")==0);

  puts("OK");
  return 0;
}
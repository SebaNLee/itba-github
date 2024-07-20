#include <stdio.h>
#include <string.h>
#include <assert.h>

/* Renombramos las funciones para poder usar el resto de string.h */
char * my_strncpy(char *target, const char *source, unsigned int n);
char * my_strncat(char *target, const char *source, unsigned int n);
int my_strncmp(char *s, char *t, unsigned int n);


int main(void) {
  char s[100];
  for(int i=0;i<100;i++)
    s[i]='x';
  assert(my_strncpy(s,"prueba copiar",100)==s);
  assert(strcmp(s,"prueba copiar")==0);
  for(int i=strlen(s); i < 100;i++)
    assert(s[i]=='\0');

  my_strncpy(s,"",100);
  for(int i=0; i < 100;i++)
    assert(s[i]=='\0');

  my_strncpy(s,"123456",4);
  assert(strcmp(s,"1234")==0);
  
  my_strncpy(s,"777777777",2);
  assert(strcmp(s,"7734")==0);

  assert(my_strncat(s, " end",10)==s);
  assert(strcmp(s,"7734 end")==0);

  assert(my_strncat(s, "abcde",2)==s);
  assert(strcmp(s,"7734 endab")==0);

  assert(my_strncmp(s,"7734 endab",10)==0);
  assert(my_strncmp(s,"7734 endab",12)==0);
  assert(my_strncmp(s,"7734",4)==0);
  assert(my_strncmp(s,"7735",4)<0);
  assert(my_strncmp(s,"7732",4)>0);
  assert(my_strncmp(s,"7734",0)==0);
  assert(my_strncmp(s,"",4)>0);
  assert(my_strncmp(s,"",0)==0);
  
  printf("OK\n");
  return 0;
}
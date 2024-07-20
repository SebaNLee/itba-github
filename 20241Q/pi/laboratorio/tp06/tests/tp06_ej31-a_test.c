#include <stdio.h>
#include <assert.h>

int main(void) {
  assert(strend("","")==1);
  assert(strend("something","")==1);
  assert(strend("something","thing")==1);
  assert(strend("something","something")==1);
  assert(strend("something","plus something")==0);
  assert(strend("not at the eeeeeend"," end")==0);
  assert(strend("not at the eeeeeennnnnd","end")==0);
  assert(strend("","s")==0);
  assert(strend("ssss","sss")==1);
  assert(strend("ssss","sssss")==0);
  puts("OK!");
  return 0;
}
#include <assert.h>
#include <stdio.h>

int
eliminaRepetidos( const int original[], int dimOrig, int resultado[]);

int main(void) {
  int v[] = { 3, 5, 1, 3, -3, 3, 5, 1};
  int res[20];
  int dimRes;

  dimRes = eliminaRepetidos(v, 0, res);
  assert(dimRes==0);

  dimRes = eliminaRepetidos(v, 1, res);
  assert(dimRes==1);
  assert(res[0]==3);

  dimRes = eliminaRepetidos(v, 2, res);
  assert(dimRes==2);
  assert(res[0]==3);
  assert(res[1]==5);

  dimRes = eliminaRepetidos(v, 4, res);
  assert(dimRes==3);
  assert(res[0]==3);
  assert(res[1]==5);
  assert(res[2]==1);


  dimRes = eliminaRepetidos(v, 8, res);
  assert(dimRes==4);
  assert(res[0]==3);
  assert(res[1]==5);
  assert(res[2]==1);
  assert(res[3]==-3);

  for (int i=0; i < 8; i++)
     v[i] =10;
  dimRes = eliminaRepetidos(v, 8, res);
  assert(dimRes==1);
  assert(res[0]==10);

  for (int i=0; i < 8; i++)
     v[i] =i;
  dimRes = eliminaRepetidos(v, 8, res);
  assert(dimRes==8);
  for (int i=0; i < 8; i++)
    assert(res[i]==i);

  puts("OK!");
  return 0;
}
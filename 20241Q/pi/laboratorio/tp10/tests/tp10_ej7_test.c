#include <stdio.h>
#include <assert.h>
#include "utillist.h"

TList listIntersec  (const TList list1, const TList list2);

int main(void) {

  int v[] = {1, 4, 5, 6, 7, 8};
  int w[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
  int x[] = {2, 3, 9};

  TList l1 = fromArray(v, 6);
  TList l2 = fromArray(w, 9);
  TList l3 = listIntersec(l1, l2);
  assert(checkElems(l3, v, 6));
  assert(checkElems(l1, v, 6));
  assert(checkElems(l2, w, 9));
  freeList(l3);

  l3 = listIntersec(l2, l1);
  assert(checkElems(l3, v, 6));
  freeList(l3);

  l3 = listIntersec(l2, l2);
  assert(checkElems(l3, w, 9));
  freeList(l3);

  l3 = listIntersec(l2, NULL);
  assert(l3==NULL);
  l3 = listIntersec(NULL, l1);
  assert(l3==NULL);
  l3 = listIntersec(NULL, NULL);
  assert(l3==NULL);
  freeList(l1);
  freeList(l2);

  l1 = fromArray(v, 6);
  l2 = fromArray(x, 3);
  l3 = listIntersec(l1, l2);
  assert(l3 == NULL);
  freeList(l3);
  freeList(l1);
  freeList(l2);

  printf ("OK!\n");
  return 0;
}
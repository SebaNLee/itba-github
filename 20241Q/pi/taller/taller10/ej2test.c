#include <stdio.h>
#include "utillist.h"
#include <assert.h>
#include <stdlib.h>

int main(void) {
    int v[] = { -10, -5, 0, 5, 10, 15, 20, 25};
    // Test para subList
    TList l1 = fromArray(v, 0);
    assert(subList(l1, 0, 10)==NULL);
    assert(subList(l1, 1, 2)==NULL);
    assert(subList(l1, 0, 0)==NULL);

    // Un solo elemento
    l1 = fromArray(v, 1);
    TList l2 = subList(l1, 0, 10);
    assert(checkElems(l2, v, 1));
    freeList(l2);
    l2 = subList(l1, 1, 3);
    assert(l2==NULL);
    l2 = subList(l1, -1, 0);
    assert(l2==NULL);

    freeList(l1);
    l1 = fromArray(v, 8);
    l2 = subList(l1, 0, 10);
    assert(checkElems(l2, v, 8));
    freeList(l2);
    l2 = subList(l1, 1, 3);
    assert(checkElems(l2, v+1, 3));
    freeList(l2);
    l2 = subList(l1, 7, 10);
    assert(checkElems(l2, v+7, 1));
    freeList(l2);

    freeList(l1);
    puts("OK subList");
}
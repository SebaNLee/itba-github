#include <stdlib.h>


typedef struct node{
    int elem;
    struct node * tail;
} TNode;

typedef TNode * TList;

TList subList(TList list, int from, int to)
{
    if(list == NULL || from > to)
    {
        return NULL;
    }

    if(from == 0)
    {
        TList new = malloc(sizeof(struct node));
        new->elem = list->elem;
        new->tail = subList(list->tail, from, to - 1);
        return new;
    }

    return subList(list->tail, from - 1, to - 1);
}

// gcc ej2.c ej2test.c utillist.c -o ej2 -Wall -pedantic -std=c99
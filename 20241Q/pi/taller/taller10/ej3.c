#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>

typedef struct node{
    int elem;
    struct node * tail;
} TNode;

typedef TNode * TList;

TList sortedList(TList list)
{
    if(list == NULL || list->tail == NULL)
    {
        return list;
    }

    list->tail = sortedList(list->tail);

    if(list->elem >= list->tail->elem)
    {
        TList aux = list->tail;
        free(list);
        return aux;
    }

    return list;
}

// gcc ej3.c ej3test.c utillist.c -o ej3 -Wall -pedantic -std=c99
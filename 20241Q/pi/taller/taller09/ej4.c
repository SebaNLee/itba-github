#include <stdlib.h>

typedef struct node{
    int elem;
    struct node * tail;
} TNode;

typedef TNode * TList;

typedef int (*fn) (int);

TList depuraLista(TList l, fn criterio)
{
    if(l == NULL)
    {
        return l;
    }
    if(criterio(l->elem)) // cumple con criterio, tengo que eliminar
    {
        TList aux = depuraLista(l->tail, criterio);
        free(l);
        return aux;
    }

    l->tail = depuraLista(l->tail, criterio);
    return l;
}



// gcc ej4.c ej4test.c utillist.c -o ej4 -Wall -pedantic -std=c99
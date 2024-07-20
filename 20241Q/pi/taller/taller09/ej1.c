#include <stddef.h>
#include <stdlib.h>

typedef struct node{
    int elem;
    struct node * tail;
} TNode;

typedef TNode * TList;

TList concatenarLista(TList l1, const TList l2)
{
    if(l2 == NULL) // ya no hay elementos a copiar
    {
        return l1;
    }
    
    if(l1 == NULL)
    {
        TList aux = malloc(sizeof(TNode));
        aux->elem = l2->elem;
        aux->tail = concatenarLista(NULL, l2->tail);
        return aux;
    }

    l1->tail = concatenarLista(l1->tail, l2);
    return l1;   

}


// gcc ej1.c ej1test.c utillist.c -o ej1 -Wall -pedantic -std=c99
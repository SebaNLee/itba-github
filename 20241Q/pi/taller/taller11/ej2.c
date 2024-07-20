#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int elementType;
 
typedef struct queueCDT * queueADT;
 
 typedef struct TNode{
    elementType elem;
    struct TNode * tail;
} TNode;

typedef struct queueCDT{
    TNode * first;
    TNode * last;
} queueCDT;






queueADT newQueue(void)
{
    return calloc(1, sizeof(queueCDT));
}
 
void queue(queueADT q, elementType n)
{
    TNode * aux = calloc(1, sizeof(TNode));
    aux->elem = n;
    
    if(q->last == NULL)
    {
        q->first = aux;
        q->last = aux;
        return;
    }
    else
    {
        q->last->tail = aux;
        q->last = aux;
        return;
    }
}
 
void dequeue(queueADT q, elementType * out)
{
    if(q == NULL || q->first == NULL)
        return;

    *out = q->first->elem;
    TNode * aux = q->first;
    q->first = q->first->tail;
    free(aux);
    return;
}
 
int isEmpty(queueADT q)
{
    return q->first == NULL;
}

static void freeQueueRec(TNode * curr)
{
    if(curr == NULL)
        return;

    freeQueueRec(curr->tail);
    free(curr);
    return;
}
 
void freeQueue(queueADT q)
{
    freeQueueRec(q->first);

    free(q);
    return;
}


// gcc ej2.c ej2.h ej2test.c -o ej2 -Wall -pedantic -std=c99
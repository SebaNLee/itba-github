#include <stdlib.h>
#include <ctype.h>

typedef struct Node * tList;

typedef struct Node {
    char elem;
    struct Node* tail;
} tNode;

tList strToListUpper(char *s)
{
    if( s == NULL|| s[0] == '\0')
    {
        return NULL;
    }

    if(isupper(s[0]))
    {
        tList aux = strToListUpper(s + 1);
        tList aux2 = malloc(sizeof(tNode));
        aux2->elem = s[0];
        aux2->tail = aux;
        return aux2;
    }

    tList aux = strToListUpper(s + 1);
    return aux;
}


// gcc ej1.c ej1test.c -o ej1 -Wall -pedantic -std=c99
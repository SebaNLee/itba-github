#include <stdlib.h>
#include <stdio.h>


typedef struct docNode {
    int id; // identifica al cliente
    double amount; // importe del comprobante (positivo o negativo)
    struct docNode * tail;
} docNode;

typedef docNode * docList;

typedef struct balanceNode {
    int id; // identifica al cliente
    double balance; // cuánto debe o se le debe al cliente
    struct balanceNode * tail;
} balanceNode;

typedef balanceNode * balanceList;

//


balanceNode * balance(docNode * curr);

int main(void)
{
    docList new1 = malloc(sizeof(docNode));
    new1->amount = -10;
    new1->id = 5;
    new1->tail = NULL;

    docList new2 = malloc(sizeof(docNode));
    new2->amount = -10;
    new2->id = 3;
    new2->tail = new1;

    docList new3 = malloc(sizeof(docNode));
    new3->amount = 95;
    new3->id = 3;
    new3->tail = new2;

    docList new4 = malloc(sizeof(docNode));
    new4->amount = -10;
    new4->id = 1;
    new4->tail = new3;


    balanceNode * ret = balance(new4);

    printf("%d %f\n", ret->id, ret->balance);
    printf("%d %f\n", ret->tail->id, ret->tail->balance);
    printf("%d %f\n", ret->tail->tail->id, ret->tail->tail->balance);

    
    return 0;
}




balanceNode * balance(docNode * curr)
{
  if(curr == NULL)
    return NULL;
  
  balanceNode * aux = balance(curr->tail);
 
  // si era el último o tiene diferente ID
  if(aux == NULL || aux->id != curr->id)
  {
    balanceNode * new = malloc(sizeof(balanceNode));
    new->balance = curr->amount;
    new->id = curr->id;
    new->tail = aux;
 
    return new;
  }
 
  // si tiene el mismo ID
  aux->balance += curr->amount;
  return aux;
}

// gcc verif.c -o verif -pedantic -Wall -std=c99
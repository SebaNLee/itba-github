




TList aux = malloc(sizeof(TNode)),
aux->tail 0










/////////////////

typedef struct museumTicketCDT * museumTicketADT;

typedef stuct node{
    char * name;
    struct node * tail;
} TNode;

typedef TNode * TList;

struct dayTickets{
    TList first;
    TList iter;
    size_t dayCount;
}


struct museumTicketCDT{
    struct dayTickets dayTickets[DAYS];
    size_t yearCount;
}


/////////////////



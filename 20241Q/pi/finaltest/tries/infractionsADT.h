#ifndef INFRACTIONSADT_H
#define INFRACTIONSADT_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct infractionsCDT * infractionsADT;

infractionsADT newInfractionsSystem(int dimVec);

void setInfractionName(infractionsADT infractions, char ** vecInfractionsIDs);

void addInfraction(infractionsADT infractions, size_t infractionID, const char * plate);

void freeInfractionsSystem(infractionsADT infractions);

int getDim(infractionsADT infractions);

char * getInfractionName(infractionsADT infractions, int id);

int getTotalCount(infractionsADT infractions,int id);

void orderVecInfractionsByTickets(infractionsADT infractions);

int isPresent(infractionsADT infractions , int id);

char * getPlateMaxInfraction(infractionsADT infractions, int id, size_t * count);

void orderVecInfractionsByAlpha(infractionsADT infractions);

// modificación para tries


//#define PLATE_CHARS 256
#define PLATE_CHARS ('Z' - 'A' + 1 + '9' - '0' + 1)  // alphanumerico


typedef struct TTrieNode {
	struct TTrieNode * children[PLATE_CHARS];
    size_t infractionCount; // cuenta las infracciones para la patente, si es 0 entonces no está registrada la patente
} TTrieNode;

void insertPlateTrie(TTrieNode * root, const char * plate);

char* getPlateMaxInfractionTrie(infractionsADT infractions, int index);

void freeTrie(TTrieNode * node);

int charToIndexTrie(char c);

TTrieNode * createNodeTrie(void);

#endif


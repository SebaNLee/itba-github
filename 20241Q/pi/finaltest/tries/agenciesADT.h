#ifndef AGENCIESADT_H
#define AGENCIESADT_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct agenciesCDT * agenciesADT;

agenciesADT newAgenciesSystem(int dimVec);

void addAgency(agenciesADT agencies, char * agencyName, int infractionID);

void freeAgenciesSystem(agenciesADT agencies);

void toBegin(agenciesADT agencies);

int hasNext(agenciesADT agencies);

char * next(agenciesADT agencies, int ** infractionCounter);

size_t getSizeVecCount(agenciesADT agencies);

#endif


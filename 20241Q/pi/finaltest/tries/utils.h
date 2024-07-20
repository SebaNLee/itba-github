#ifndef UTILS_H
#define UTILS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include "infractionsADT.h"
#include "agenciesADT.h"

typedef struct TCityInfractions{
	size_t ID;
	char * description;
} TCityInfractions;

typedef struct TCityTickets{
	int ID;
	char * agency;
	char * plate;
} TCityTickets;

TCityInfractions getCityInfractionsFormat(char * line);

char ** readInfractions(const char * csvFile, int * dim);

void freeInfractions(char ** descriptionID, int dim);

void readTickets(const char * csvFile, agenciesADT agencies, infractionsADT infractions, char ** descriptionID, int dimDescriptionID);

TCityTickets getCityTicketsFormat(char * line);

void query1(infractionsADT infractions);

void query2(agenciesADT agencies, char ** vecInfractionsIDs);

void query3(infractionsADT infractions);

#endif


#include "agenciesADT.h"
#include "infractionsADT.h"
#include "utils.h"

#include <time.h>
#include <stdio.h>

int main(int argc, char * argv[]){

	clock_t inicio, fin;
    double tiempo_transcurrido;


	if(argc != 3){
		fprintf(stderr, "Invalid arguments!\n");
		return EXIT_FAILURE;
	}

	int dim = 0;
	char ** vecInfractionsIDs = readInfractions(argv[2], &dim);

	agenciesADT agencies =  newAgenciesSystem(dim);
	infractionsADT infractions = newInfractionsSystem(dim);

	setInfractionName(infractions, vecInfractionsIDs);

	

	readTickets(argv[1], agencies, infractions, vecInfractionsIDs, dim);
	

	query1(infractions);
	query2(agencies, vecInfractionsIDs);
	query3(infractions);

	freeAgenciesSystem(agencies);
	freeInfractionsSystem(infractions);
	freeInfractions(vecInfractionsIDs, dim);

	printf("OK!!\n");


	fin = clock();
	tiempo_transcurrido = (double)(fin - inicio) / CLOCKS_PER_SEC;
	printf("Tiempo transcurrido: %.6f segundos\n", tiempo_transcurrido);

	return EXIT_SUCCESS;
}

// gcc main.c infractionsADT.c agencyADT.c utils.c -Wall -pedantic -std=c99 -fsanitize=address -DNYC (o -DCHI)


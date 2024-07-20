#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>

#include "../final/agencyADT.h"
#include "../final/infractionsADT.h"
#include "../final/utils.h"

// definir formatos del csv
#define FORMAT_ID "infractionId"
#define FORMAT_AGENCY "issuingAgency"
#define FORMAT_PLATE "plate"


void query1(infractionsADT infractions);

void query2(agencyADT agency);

void query3(infractionsADT infractions);

infractionsADT sortAlpha(infractionsADT infractions);


int main(int argc, char * argv[])
{
	if(argc != 3){
		fprintf(stderr, "Invalid arguments!\n");
		return EXIT_FAILURE;
	}


    int dim = 0;
    infractionsRecord * vecInfractionsIDs = readInfractions(argv[2], &dim);

    agencyADT agency =  newAgencySystem();
    infractionsADT infractions = newInfractionsSystem();

    readTickets(argv[1], agency, infractions, vecInfractionsIDs);


    query1(infractions);
    query2(agency);
    query3(infractions);
    

    void freeAgencySystem(agencyADT system);
    void freeInfractionsSystem(infractionsADT system);
    freeInfractions(vecInfractionsIDs);



	return EXIT_SUCCESS;
}

void query1(infractionsADT infractions)
{
    //TODO

    return;
}

void query2(agencyADT agency)
{
    //TODO
    
    return;
}

void query3(infractionsADT infractions)
{
    //TODO
    
    return;
}

infractionsADT sortAlpha(infractionsADT infractions)
{
    //TODO
    // para usar en query3 


    // modificar el valor de retorno, puesto para que compile
    return infractions;
}




// gcc test.c ../final/infractionsADT.c ../final/agencyADT.c ../final/utils.c -o test -Wall -pedantic -std=c99 -fsanitize=address






/*


////////////////////////

typedef struct infractionsCount {
    size_t ID;
    size_t count;
} infractionsCount;

struct agencyNode {
    infractionsCount * vecInfractionsCount; // vector contador para cada infraccion
    size_t size; // dim para el vector de infracciones
    struct agencyNode * tail; // puntero al siguiente nodo
} agencyNode;

typedef struct agencyNode * agencyList;

typedef struct agencyCDT {
    agencyList firstAgency; // puntero al primer agency
} agencyCDT;

/////////////////

typedef struct TPlate {
    char * plate; // patente
    size_t countInfractions; // contador de infracciones
} TPlate;

typedef struct TInfraction {
    TPlate * vecPlate; // vector de patentes
    char * infractionName; // guardo el nombre de la infraccion para poder ordenarlo después
    size_t totalCount; // contador de infracciones
    size_t sizePlate; // dim para el vector de patentes
} TInfraction;


typedef struct infractionsCDT {
    TInfraction * vecInfractions; // vector de infracciones
    size_t size; // dim para el vector de infracciones
} infractionsCDT;


////////

// agregar vector auxiliar








*/













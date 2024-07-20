#include "agenciesADT.h"

struct agenciesNode {
	char * agencyName;
	int * vecCount; // vector contador para cada infraccion
	struct agenciesNode * tail; // puntero al siguiente nodo
} agenciesNode;

typedef struct agenciesNode * agenciesList;

typedef struct agenciesCDT {
	agenciesList firstAgency; // puntero al primer agency
	agenciesList iterator ; // iterador
	size_t sizeVecCount; // dim para el vector de infracciones
} agenciesCDT;

agenciesADT newAgenciesSystem(int dimVec){
	errno = 0;
	agenciesADT new = malloc(sizeof(agenciesCDT));
	if(new == NULL || errno == ENOMEM)
	{
		perror("Error no memory.\n");
		exit(EXIT_FAILURE);
	}

	new->firstAgency = NULL;
	new->sizeVecCount = dimVec;

	return new;
}

static char * copyStr(const char * s){
	char * ans = malloc(strlen(s)+1);
	if(ans == NULL || errno == ENOMEM){
		perror("Error allocating memory.\n");
		exit(EXIT_FAILURE);
	}
	strcpy(ans, s);
	return ans;
}

static agenciesList addAgencyRec(agenciesList list, const char * agencyName, int infractionID, size_t dim){
	int c;
	if (list == NULL || (c = strcmp(list->agencyName, agencyName)) > 0) {
		errno = 0;
		agenciesList aux = malloc(sizeof(struct agenciesNode));
		if (aux == NULL || errno == ENOMEM) {
			perror("Error allocating memory.\n");
			exit(EXIT_FAILURE);
		}

		aux->agencyName = copyStr(agencyName);
		aux->vecCount = calloc(dim, sizeof(*aux->vecCount));
		if (aux->vecCount == NULL || errno == ENOMEM) {
			perror("Error allocating memory.\n");
			exit(EXIT_FAILURE);
		}
		aux->vecCount[infractionID] = 1;
		aux->tail = list;
		return aux;
	}

	if(c == 0){
		list->vecCount[infractionID]++;
		return list;
	}

	list->tail = addAgencyRec(list->tail, agencyName, infractionID, dim);
	return list;
}

void addAgency(agenciesADT agencies, char * agencyName, int infractionID){
	agencies->firstAgency = addAgencyRec(agencies->firstAgency, agencyName, infractionID, agencies->sizeVecCount);
}

static void freeList(agenciesList list){
	if(list == NULL)
		return;

	freeList(list->tail);
	free(list->vecCount);
	free(list->agencyName);
	free(list);
}

void freeAgenciesSystem(agenciesADT agencies){
	freeList(agencies->firstAgency);
	free(agencies);
	return;
}

void toBegin(agenciesADT agencies){
	agencies->iterator = agencies->firstAgency;
}

int hasNext(agenciesADT agencies){
	return agencies->iterator != NULL;
}

char * next(agenciesADT agencies, int ** infractionCounter){
	if (!hasNext(agencies)){
		fprintf(stderr, "No next here!\n");
		exit(EXIT_FAILURE) ;
	}

	*infractionCounter = agencies->iterator->vecCount;
	char * toReturn = copyStr(agencies->iterator->agencyName);
	agencies->iterator = agencies->iterator->tail;

	return toReturn ;
}

size_t getSizeVecCount(agenciesADT agencies){
	return agencies->sizeVecCount;
}


#include "infractionsADT.h"

typedef struct TInfraction {
	TTrieNode * root; // root del trie de patentes
	char * infractionName; // guardo el nombre de la infraccion para poder ordenarlo después
	size_t totalCount; // contador total de infracciones
} TInfraction;


typedef struct infractionsCDT {
	TInfraction * vecInfractions; // vector de infracciones
	size_t size; // dim para el vector de infracciones
} infractionsCDT;


infractionsADT newInfractionsSystem(int dim){
	infractionsADT system = calloc(1,sizeof(infractionsCDT));
	if(system == NULL || errno == ENOMEM){
		perror("Error allocating memory.\n");
		exit(EXIT_FAILURE);
	}
	system->vecInfractions = calloc(dim,sizeof(TInfraction)); //reservo espacio para el vector de infracciones
	if(system->vecInfractions == NULL || errno == ENOMEM){ //valido que se haya reservado bien
		perror("Error allocating memory.\n");
		exit(EXIT_FAILURE);
	}
	system->size = dim;

	// inicializo nodos root de los tries
	for(int i = 0; i < dim; i++)
	{
		system->vecInfractions[i].root = createNodeTrie();
	}

	return system;
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

void setInfractionName(infractionsADT infractions, char ** vecInfractionsIDs)
{
	for(int i = 0; i < infractions->size; i++)
	{
		if(vecInfractionsIDs[i] != NULL)
		{
			infractions->vecInfractions[i].infractionName = copyStr(vecInfractionsIDs[i]);
		}
	}

	return;
}

void addInfraction(infractionsADT infractions, size_t infractionID, const char * plate) {

	// modifico la funcion para tries

	insertPlateTrie(infractions->vecInfractions[infractionID].root, plate);
	infractions->vecInfractions[infractionID].totalCount += 1;

	return;
}

void freeInfractionsSystem(infractionsADT infractions){
	for(int i = 0 ; i < infractions->size ; i++){
		freeTrie(infractions->vecInfractions[i].root);
		free(infractions->vecInfractions[i].infractionName);
	}
	
	free(infractions->vecInfractions);
	free(infractions);

	return;
}

//para la query 1:
static int compareInfractionsByTickets(const void * a, const void * b){ //recibe un tipo de objeto generico (void *)
	TInfraction * inf1 = (TInfraction *)a; //castea a TInfraction *
	TInfraction * inf2 = (TInfraction *)b;
	if(inf1->totalCount > inf2->totalCount){
		return -1;
	}
	else if(inf1->totalCount < inf2->totalCount){
		return 1;
	}
	else{ //si tienen la misma cantidad de infracciones, desempato por nombre
	      //primero hay que validar que ninguno sea null!!
		if(inf1->infractionName == NULL && inf2->infractionName == NULL){
			return 0;
		}
		else if(inf1->infractionName == NULL){
			return 1;
		}
		else if(inf2->infractionName == NULL){
			return -1;
		}
		return strcmp(inf1->infractionName,inf2->infractionName);
	}

}

void orderVecInfractionsByTickets(infractionsADT infractions){
	qsort(infractions->vecInfractions,(infractions->size),sizeof(TInfraction), ((int (*) (const void * , const void *))compareInfractionsByTickets) );
	return;
}

int isPresent(infractionsADT infractions , int id){
	if(infractions->vecInfractions[id].infractionName != NULL){
		return 1;
	}
	return 0;
}

int getDim(infractionsADT infractions){
	return infractions->size;
}

char * getInfractionName(infractionsADT infractions, int id){
	return infractions->vecInfractions[id].infractionName;
}

int getTotalCount(infractionsADT infractions,int id){
	return infractions->vecInfractions[id].totalCount;
}

//para la query 3:
//de una infraccion especifica (id) devuelve la patente con mas multas (patente y cant de multas)
char * getPlateMaxInfraction(infractionsADT infractions, int id, size_t *count) {
	return NULL;
	/*
	TInfraction * infraction = &infractions->vecInfractions[id];

	if (infraction->sizePlate == 0) {
		// No hay patentes para esta infracción
		*count = 0;
		return NULL;
	}

	// Encontrar la patente con el mayor número de infracciones
	TPlate * maxPlate = &infraction->vecPlate[0];
	for (size_t i = 1; i < infraction->sizePlate; i++) {
		if (infraction->vecPlate[i].countInfractions > maxPlate->countInfractions)
			maxPlate = &infraction->vecPlate[i];
	}

	*count = maxPlate->countInfractions;
	return maxPlate->plate;
	*/
}

int compareInfractionsByAlpha(const void *a, const void *b) {
	TInfraction *inf1 = (TInfraction *)a;
	TInfraction *inf2 = (TInfraction *)b;
	if(inf1->infractionName == NULL || inf2->infractionName == NULL){
		return 0;
	}
	else if(inf1->infractionName == NULL){
		return 1;
	}
	else if( inf2->infractionName == NULL){
		return -1;
	}
	return strcmp(inf1->infractionName, inf2->infractionName);
}

//ordena en orden alfabetico:
void orderVecInfractionsByAlpha(infractionsADT infractions){
	qsort(infractions->vecInfractions,infractions->size,sizeof(TInfraction),compareInfractionsByAlpha);
	return;
}




// agrego funciones para tries

void insertPlateTrie(TTrieNode * root, const char * plate)
{
	
	TTrieNode * node = root;
	int plateLength = strlen(plate);

	for(int i = 0; i < plateLength; i++)
	{
		int index = charToIndexTrie(plate[i]);

		if(index == -1)
		{
			printf("Invalid plateIndex in trie insertion: %s\n", plate);
			return;
		}

		if(node->children[index] == NULL)
		{
			node->children[index] = createNodeTrie();
		}

		node = node->children[index];
	}

	node->infractionCount += 1;
	
	return;
}

void findMostInfractedPatenteHelper(TTrieNode *node, char *currentPlate, int level, char **mostInfractedPlate, int *maxInfractions) {
    if (node == NULL) return;

    // Si este nodo tiene más infracciones que el máximo actual, actualiza el máximo
    if (node->infractionCount > *maxInfractions) {
        *maxInfractions = node->infractionCount;
        // Libera la memoria anterior de la patente más infraccionada
        free(*mostInfractedPlate);
        // Asigna memoria para la nueva patente y cópiala
        *mostInfractedPlate = (char *)malloc((level + 1) * sizeof(char));
        strncpy(*mostInfractedPlate, currentPlate, level);
        (*mostInfractedPlate)[level] = '\0';
    }

    // Recorre todos los hijos del nodo actual
    for (int i = 0; i < PLATE_CHARS; i++) {
        if (node->children[i] != NULL) {
            // Añade el carácter actual a la patente en construcción
            currentPlate[level] = i < 26 ? 'A' + i : '0' + (i - 26);
            // Llama recursivamente a la función para el hijo
            findMostInfractedPatenteHelper(node->children[i], currentPlate, level + 1, mostInfractedPlate, maxInfractions);
        }
    }
}

char* getPlateMaxInfractionTrie(infractionsADT infractions, int index) {
    
	TTrieNode * root = infractions->vecInfractions[index].root;
	
	if (root == NULL) return NULL;

    char currentPlate[256]; // Suponiendo que las patentes no tienen más de 255 caracteres
    char *mostInfractedPlate = NULL;
    int maxInfractions = 0;

    findMostInfractedPatenteHelper(root, currentPlate, 0, &mostInfractedPlate, &maxInfractions);

    return mostInfractedPlate;
}


void freeTrie(TTrieNode * node)
{
	if(node == NULL)
		return;

	for(int i = 0; i < PLATE_CHARS; i++)
	{
		if(node->children[i] != NULL)
		{
			freeTrie(node->children[i]);
		}
	}

	free(node);
}

int charToIndexTrie(char c)
{

	if(c >= 'A' && c <= 'Z')
	{
		return c - 'A';
	}
	else if(c >= '0' && c <= '9')
	{
		return 'Z' - 'A' + 1 + c - '0';
	}
	else
	{
		return -1;
	}

	// return c;

}

TTrieNode * createNodeTrie(void)
{
	TTrieNode * newNode = malloc(sizeof(TTrieNode));
	if(newNode == NULL)
	{
		perror("Error allocating memory.\n");
		exit(EXIT_FAILURE);
	}

	newNode->infractionCount = 0;

	for(int i = 0; i < PLATE_CHARS; i++)
	{
		newNode->children[i] = NULL;
	}

	return newNode;
}
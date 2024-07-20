#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct TCityInfractions{
	size_t ID;
	char * description;
} TCityInfractions;

TCityInfractions getCityInfractionsFormat(char * line);

char ** readInfractions(const char * csvFile, int * dim);


#define MAX 256

static char * copyStr(const char * s){
	char * ans = malloc(strlen(s)+1);
	errno = 0;
	if(ans == NULL || errno == ENOMEM){
		perror("Error allocating memory.\n");
		exit(EXIT_FAILURE);
	}
	return strcpy(ans, s);
}

TCityInfractions getCityInfractionsFormat(char * line){
	TCityInfractions cityFormat;
	char tempDescription[MAX];
#ifdef NYC
	sscanf(line, "%zu;%255[^\n]", &cityFormat.ID, tempDescription);
#endif
#ifdef CHI
	sscanf(line, "%zu;%255[^\n]", &cityFormat.ID, tempDescription);
#endif
	cityFormat.description = copyStr(tempDescription);
	return cityFormat;
}

char ** readInfractions(const char * csvFile, int * dim){
	FILE * f = fopen(csvFile, "r");
	if(f == NULL){
		perror("Error: file not opened.\n");
		return NULL;
	}

	static char buffer[MAX];
	char ** ans = NULL;
	*dim = 0;

	if(fgets(buffer, sizeof(buffer), f) == NULL){
		perror("Error: failed to read header.\n");
		fclose(f);
		return NULL;
	}


	while(fgets(buffer, sizeof(buffer), f)){
		TCityInfractions cityFormat = getCityInfractionsFormat(buffer);

		if(cityFormat.ID >= *dim){
			errno = 0;
			ans = realloc(ans, (cityFormat.ID+1)*sizeof(char *));
			if(ans == NULL || errno == ENOMEM){
				perror("Error reallocating memory.\n");
				return NULL;
			}
			for(int i = *dim; i <= cityFormat.ID; i++){
				ans[i] = NULL;
			}
			*dim = cityFormat.ID+1;
		}

		ans[cityFormat.ID] = cityFormat.description;
		//ans[cityFormat.ID] = copyStr(cityFormat.description);
		//free(cityFormat.description);
	}

	fclose(f);
	return ans;
}


int main(int argc, char *argv[]) {
	if (argc < 2) {
		fprintf(stderr, "Uso: %s archivo.csv\n", argv[0]);
		return 1;
	}

	const char *csvFile = argv[1];
	int dim;
	char **infractions = readInfractions(csvFile, &dim);

	if (infractions == NULL) {
		fprintf(stderr, "Error al leer las infracciones del archivo.\n");
		return 1;
	}

	printf("Infracciones leídas correctamente:\n");
	for (int i = 0; i < dim; i++) {
		if (infractions[i] != NULL) {
			printf("ID %d: %s\n", i, infractions[i]);
		}
	}

	// Liberar memoria asignada a cada descripción
	for (int i = 0; i < dim; i++) {
		free(infractions[i]);
	}
	// Liberar el arreglo de punteros
	free(infractions);

	return 0;
}

// gcc test3.c -Wall -pedantic -std=c99 -fsanitize=address -g -DNYC
// GUARDA EL -D !!!!!!!
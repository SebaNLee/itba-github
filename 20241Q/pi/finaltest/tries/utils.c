#include "utils.h"
#include "infractionsADT.h"

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

char ** readInfractions(const char * csvFile, int * dim){//devuelve un vector de strings con las descripciones de las infracciones
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
	}

	fclose(f);
	return ans;
}

void freeInfractions(char ** descriptionID, int dim){
	for(int i = 0; i < dim; i++){
		free(descriptionID[i]);
	}
	free(descriptionID);
	return;
}

TCityTickets getCityTicketsFormat(char * line){
	TCityTickets cityFormat;
	char tempPlate[MAX];
	char tempAgency[MAX];
#ifdef NYC
	//char tempPlate[NYC_MAX_PLATE];
	//char tempAgency[];
	sscanf(line, "%10[^;];%*[^;];%d;%*d;%35[^;\n]", tempPlate, &cityFormat.ID, tempAgency);
#endif
#ifdef CHI
	sscanf(line, "%*[^;];%10[^;];%13[^;];%d;%*d", tempPlate, tempAgency, &cityFormat.ID);
#endif
	cityFormat.agency = copyStr(tempAgency);
	cityFormat.plate = copyStr(tempPlate);
	return cityFormat;
}

void readTickets(const char * csvFile, agenciesADT agencies, infractionsADT infractions, char ** descriptionID, int dimDescriptionID){
	FILE * fpCSV = fopen(csvFile, "r");

	if(fpCSV == NULL){
		perror("Error: No se pudo abrir el csv.");
		exit(EXIT_FAILURE);
	}

	char buffer[MAX];

	// lee el header y lo descarta
	if(fgets(buffer, sizeof(buffer), fpCSV) == NULL){
		perror("Error: failed to read header.\n");
		fclose(fpCSV);
		exit(EXIT_FAILURE);
	}

	while(fgets(buffer, sizeof(buffer), fpCSV)){
		// obtengo por linea los datos a utilizar
		TCityTickets cityFormat = getCityTicketsFormat(buffer);

		// check si multa es válida
		if(cityFormat.ID <= dimDescriptionID && descriptionID[cityFormat.ID] != NULL){

			addAgency(agencies, cityFormat.agency, cityFormat.ID);

			addInfraction(infractions, cityFormat.ID, cityFormat.plate);
		}

		free(cityFormat.agency);
		free(cityFormat.plate);
	}

	return;
}

FILE * newFile(const char * filename){
	FILE * new = fopen(filename, "wt");
	return new;
}

//Total de multas por infracción
void query1(infractionsADT infractions){

	orderVecInfractionsByTickets(infractions); //ordena el vector infractions x cantidad de multas (decreciente) y desempata x nombre de infraccion (alfabeticamente)

	//idea: recorro el vector vecInfractions en infracttions(TAD) --- ese es el primer campor de la "respuesta" a esta query
	// cada elemento de ese vector contiene un campo llamado totalCount que es el total de infracciones de ese tipo --- el segundo campo de la respuesta

	FILE * query1File = newFile("query1.csv");
	if(query1File==NULL){
		fprintf(stderr,"Error al crear archivo query1\n");
		exit(EXIT_FAILURE);
	}
	fputs("Infraction;Total\n",query1File); //encabezado

	int dim = getDim(infractions);

	for(int i = 0 ; i<dim ; i++){ //x cada infraccion...
		if(isPresent(infractions , i)){ //no puedo asumir que en cada lugar del vector infractions haya una infraccion (podria haber espacios vacios x el tema del matcheo con el id) asi q necesito una funcnion que valide eso
			fprintf(query1File,"%s;%d\n",getInfractionName(infractions,i),getTotalCount(infractions,i)); //escribo en el archivo
		}
	}

	fclose(query1File);
	return;
}

//Infracción más popular por agencia emisora
void query2(agenciesADT agencies, char ** vecInfractionsIDs){
	FILE * query2File = newFile("query2.csv");
	if(query2File==NULL){
		fprintf(stderr,"Error al crear archivo query1\n");
		exit(EXIT_FAILURE);
	}
	fputs("Agency;Infraction;Tickets\n",query2File); //encabezado

	int * infractionCounter;

	toBegin(agencies);
	//recorro la lista de infracciones del tad agencies
	while(hasNext(agencies)){
		char * agencyName  = next(agencies  , &infractionCounter);

		size_t dim = getSizeVecCount(agencies);
		int max = 0;
		size_t id = 0;
		for(size_t i = 1 ; i<dim ; i++ ){

			if(infractionCounter[i] > max){
				max = infractionCounter[i];
				id = i;
			}
		}
		//index es el ID de la infraccion
		char * infraction = vecInfractionsIDs[id];

		//aca tengo: el nombre de la agencia en agencyName , el nombre de la infraccion en infraction y la cantidad de tickets en max
		fprintf(query2File,"%s;%s;%d\n",agencyName,infraction,max);

		free(agencyName);
	}

	fclose(query2File);
	return;
}

void query3(infractionsADT infractions){ // Patente con más multas por infracción

	//lo primero que hago es ordenar las infracciones en orden alfabetico
	orderVecInfractionsByAlpha(infractions);

	//idea: recorro todas las infracciones ------ primer campo de la respuesta
	// y por cada agencia, recorro todas las patentes que tienen multas de esa infraccion (osea el vector vecPlate). Comparo el campo countInfractions de cada patente y me voyquedando con el mayor
	//cuando termine de recorrer ese vector de patentes vecPlate, me habre quedado con el que tiene mas multas. De esa patente, el string plate es el segundo campo de la respuesta y el 3er campo es countInfractions

	FILE * query3File = newFile("query3.csv");
	if(query3File==NULL){
		fprintf(stderr,"Error al crear archivo query1\n");
		exit(EXIT_FAILURE);
	}
	fputs("Infraction;Plate;Tickets\n",query3File); //encabezado

	int dim = getDim(infractions);
	//toReturn:
	char * plate;
	size_t tickets; //seria countInfractions en la definicion de TPlate
	for(int i = 0 ; i<dim ; i++){//x cada infraccion del vector infractions...
		if(isPresent(infractions, i)){
			plate = getPlateMaxInfractionTrie(infractions, i); //devuelve la patente con mas multas de esa infraccion
										   //ahora tengo la patente con mas infracciones en "plate" y la cantidad de infracciones en "tickets"
			if(plate != NULL)
				fprintf(query3File,"%s;%s;%ld\n",getInfractionName(infractions,i),plate,tickets); //escribo en el archivo

			free(plate);
		}
	}

	fclose(query3File);
	return;
}


#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

// Vector de punteros a char, tambien podría ser matriz de chars
typedef char * TAlumnos[];

char ** aprobados(TAlumnos alumnos, int notas[]);


int main(void){

    TAlumnos alumnos = {"Juan", "Pedro", "Martina", ""};
    int notas[] = {1, 4, 10, 2, 10, 11};
    
    char ** apr = aprobados(alumnos, notas);
    if(apr == NULL){
        printf("No hay suficiente memoria!\n");
        return 1;
    }
    assert(!strcmp(alumnos[1], apr[0]) && !strcmp(alumnos[2], apr[1]) && !strcmp(alumnos[3], apr[2]));
	// En esta parte no liberamos la memoria que utilizamos. Que ocurre si compilamos con "-fsanitize=address"?
    puts("OK test simple!");
	
	// Armamos un vector con miles de aprobados al azar
	srand(time(NULL));
	int dim = 100000;
	// Por como se hizo el typedef tenemos que inicializarlo, para poder hacer una prueba con miles de elementos
	// creados, es que no usamos el typedef
	char * alumnos2[dim];
	char aprob[dim];
    int notas2[dim], nota, cantAprob=0;
	for(int i=0; i<dim-1; i++) {
		nota = rand() % 10 + 1; // entre 1 y 10 en forma simple
		alumnos2[i] = malloc(20);
		sprintf(alumnos2[i], "Alumno %d: %d", i, nota);
		aprob[i] = nota >= 4;   // NOTA_MINIMA deberia estar en un .h
		cantAprob += nota >= 4;
		notas2[i] = nota;
	}
	alumnos2[dim-1] = "";
	apr = aprobados(alumnos2, notas2);
	
	int dimApr=0;
	int i=0;  // Para buscar el siguiente aprobado
	while(apr[dimApr][0]) {
		while(!aprob[i]) {
			i++;
		}
		assert(! strcmp(apr[dimApr], alumnos2[i]));
		dimApr++;
		i++;
	}
	assert(cantAprob==dimApr);
	
	
	for(int i=0; i<dim-1; i++) {
		free(alumnos2[i]);
	}
    puts("OK test completo!");	
    
    return 0;
}
#include <stdlib.h>
#include <string.h>
#include <stdio.h>


// .c


typedef struct palabraCDT{
    char * word;
    size_t dim;
} palabraCDT;

typedef palabraCDT * palabraADT;


// .h


palabraADT crearVacia();

palabraADT crearDesde(char *s);

palabraADT agregarLetra(palabraADT pal, char letra);

void mostrarPalabra(palabraADT pal);

void destruirPalabra(palabraADT pal);


// .c


palabraADT crearVacia()
{
    return calloc(1, sizeof(palabraCDT));
}

palabraADT crearDesde(char *s)
{
    size_t newDim = strlen(s);
    palabraADT aux = malloc(sizeof(palabraCDT));
    aux->word = malloc(sizeof(sizeof(char) * newDim));
    strcpy(aux->word, s);
    aux->dim = newDim;
    return aux;
}


palabraADT agregarLetra(palabraADT pal, char letra)
{
    pal->word = realloc(pal->word, sizeof(char) * (pal->dim + 1));
    pal->word[pal->dim] = letra;
    pal->dim += 1;
    return pal;
}


void mostrarPalabra(palabraADT pal)
{
    printf("%s\n", pal->word);
    return;
}

void destruirPalabra(palabraADT pal)
{
    if(pal != NULL)
    {
        free(pal->word);
        free(pal);
    }
    return;
}


// testeo

int main(void)
{
    palabraADT aux;
    
    aux = crearDesde("hola");

    aux = agregarLetra(aux, 's');

    mostrarPalabra(aux);

    printf("%s\n", aux->word);
    printf("%ld\n", aux->dim);

    destruirPalabra(aux);
}

// gcc ej1.c -o ej1 -Wall -pedantic -std=c99
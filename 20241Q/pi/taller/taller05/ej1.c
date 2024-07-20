#include <stdio.h>


int unDiaMas(int * dia, int * mes, int * anio);



// código provisto por la cátedra (para años bisiesto)

int daytab[2][12] = {{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
                     { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }};

static int
bisiesto(int anio) {
    int esb=0;
    if((anio%4 == 0 && anio%100!= 0) || anio%400 == 0)
        esb=1;
    return esb;
}

// hasta acá

/*
int main(void)
{
    int dia, mes, anio;

    dia = 1;
    mes = 2;
    anio = 2001;

    printf("Día: %d Mes: %d Anio: %d\n", dia, mes, anio);

    unDiaMas(&dia, &mes, &anio);

    printf("Día: %d Mes: %d Anio: %d\n", dia, mes, anio);

    return 0;
}
*/

// devuelve 0 si error
int unDiaMas(int * dia, int * mes, int * anio)
{

    // flag es bisiesto
    int flagBisiesto = 0;

    // validación de mes
    if(*mes > 12 || *mes < 1)
    {
        return 0;
    }

    if(bisiesto(*anio))
    {
        flagBisiesto = 1;
    }

    // validación de días
    if(*dia > daytab[flagBisiesto][*mes - 1])
    {
        return 0;
    }

    // modificación de valores por casos
    if(*dia == 31 && *mes == 12) // si es fin de año
    {
        *dia = 1;
        *mes = 1;
        *anio = *anio + 1;
    }
    else if(daytab[flagBisiesto][*mes - 1] == *dia) // si es fin de mes
    {
        *dia = 1;
        *mes = *mes + 1;
    }
    else // si no, es día genérico
    {
        *dia = *dia + 1;
    }


    return 1;
}

// obs: hay código repetido, es mejor incrementar día, después mes (de ser necesario) e ídem con anio



// gcc ej1.c -o ej1 -Wall -pedantic -std=c99
// gcc ej1.c ej1_test.c -o ej1 -Wall -pedantic -std=c99




#include <stdio.h>


int llegaTemprano (const int hora, const int minutos){

    return (hora <= ENT_HORA && minutos <= ENT_MINUTOS);

}

// por ejemplo, si llegar después de 09:30 es considerado tarde, 08:30 es falso (error)

// lo corrijo con:

    return (hora < ENT_HORA || (hora == ENT_HORA && minutos <= ENT_MINUTOS));
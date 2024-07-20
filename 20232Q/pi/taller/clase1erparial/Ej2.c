#include <stdio.h>
#include <ctype.c>


int elimina(char string[]){

    int longitud;

    longitud = sizeof(string[]) / string[0];

    for(int i = 0; string[i]; i++){

        if(!(isalpha(string[i]))){


            for(int j = i; string[j + 1]; j++){
                
                string[j] = string[j + 1];

            }   


            longitud--;
        }


    }

    return longitud;
}

// no sé si el código está bien, pero no es eficiente por correr todo por cada letra
// chequear solución de campus
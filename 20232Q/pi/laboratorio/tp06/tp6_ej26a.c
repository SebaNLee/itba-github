



#include <stdlib.h>


void copiarString(char *indice, char *string2){

    while(*string2){
        *indice++ = *string2++;
    }
}


char * encuentraCarac(char *string1, char c){

    int i = 0;

    while(*string1){

        if(string1[i] == c){
            return &string1[i];
        }
        i++;
    }

    return NULL;
}


void insertaDesde(char *string1, char *string2, char c){

    char *indice = encuentraCarac(string1, c);

    if(indice != NULL){
        copiarString(indice, string2);
    }


}








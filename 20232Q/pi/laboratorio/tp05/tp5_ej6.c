#include <stdio.h>
#include <ctype.h>
#include "getnum.h"


int aMinus(int caracter);
int aMayus(int caracter);
int printfSiguiente(int caracter);
int printfFormCirc(int caracter);
int menu(void);

int main(void){

    int caracter, comando, resp;

    do{
        printf("Ingrese un carácter: ");
        caracter = getchar();

        if(caracter != EOF){
            comando = menu();
        }

        switch(comando){
            case 1: 
                resp = aMinus(caracter); 
                break;
            case 2: 
                resp = aMayus(caracter);
                break;
            case 3: 
                resp = printfSiguiente(caracter);
                break;
            case 4: 
                resp = printfFormCirc(caracter);
                break;
            default:
                resp = 0;
                printf("Opción inválida\n");
        }
        
        if(resp != 0){
            printf("Respuesta: %c\n\n\n", resp);
        }

    }while(caracter != EOF);




    return 0;
}

int aMinus(int caracter){


    if(caracter >= 'A' && caracter <= 'Z'){
        caracter = tolower(caracter);
    }

    return caracter;
}


int aMayus(int caracter){


    if(caracter >= 'a' && caracter <= 'z'){
        caracter = toupper(caracter);
    }

    return caracter;
}


int printfSiguiente(int caracter){

    return caracter + 1;
}


int printfFormCirc(int caracter){

    if((caracter >= 'A' && caracter <'Z') || (caracter >= 'a' && caracter < 'z')){
        caracter = printfSiguiente(caracter);
    }
    else if(caracter == 'z' || caracter == 'Z'){
        caracter -= ('Z' - 'A');
    }

    return caracter;
}


int menu(void){

    int opcion;

    printf("1 - Pasar a minúsuclas\n");
    printf("2 - pasar a mayúsculas\n");
    printf("3 - Imprimir el siguiente carácter\n");
    printf("4 - Imprimir de forma circular\n");
    
    opcion = getint("Ingrese una opción: ");

    return opcion;
}




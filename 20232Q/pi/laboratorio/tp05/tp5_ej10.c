#include <stdio.h>
#include <math.h>
#include "getnum.h"

#define LEO_DENOM_NO_CERO(denom) {do {denom = getint(""); \
                                    if(denom == 0) printf("El denominador no puede ser cero");\
                                    }while(denom == 0);}


int menu();
void dcm();
void simplFrac(int num1, int den1);
void sumarFrac(int num1, int num2, int dem1, int dem2);



int main(void){

    int opcion;
    int num1, num2, den1, den2;

    do{

        opcion = menu()

        switch(opcion){
            case 1:
                num1 = getint("Ingrese el numerador: ");
                printf("Ingrese el denominador");
                LEO_DENOM_NO_CERO(den1);
                simplFrac(int num1, int den1);
                break;
            case 2:
                num1 = getint("Ingrese el numerador de la 1ra fracción: ");
                printf("Ingrese el denominadorde la 1ra fracción");
                LEO_DENOM_NO_CERO(den1);
                num1 = getint("Ingrese el numerador de la 2da fracción: ");
                printf("Ingrese el denominadorde la 2da fracción");
                LEO_DENOM_NO_CERO(den2);
                sumarFrac(num1, num2, den1, den2);
                break;
            case 3:
                break;
            default:
            printf("Opción inválida\n");
            break;
        }

    }while(opcion != 3);

    return 0;
}


int menu(){

    printf("1 - Simplificar una fracción\n");
    printf("2 - Sumar dos fracciones\n");
    printf("3 - Terminar la ejecución\n");
    
    int opcion = getint("Ingrese una opción");
    return opcion;
}


void simplFrac(int num1, int den1){


}


void sumarFrac(int num1, int num2, int dem1, int dem2){


}


? dcm();

// no lo termino, me dio fiaca
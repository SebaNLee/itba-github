#include <stdio.h>


void sumarD(int *d);
void sumarM(int *d, int *m);
void sumarY(int *d, int *m, int *y);
static int esBisiesto(int anio); 


int unDiaMas(int *d, int *m, int *y){ // recibe direcciones mandadas por &var


    if(*d == NULL || *m == NULL || *y == NULL || *d < 1 || *d > 31){
        return 0; // 0 para errores y 1 para válidos
    }


    if(esBisiesto(*y != 0 && *d == 29 && *m == 2)){ // caso especial, año bisesto
        *d = 1;       
        *m = *m + 1;
        return 1;
    }

    switch(*d){
        
        case 30:
        if(*m == 4 || *m == 6 || *m == 9 || *m == 11){
            void sumarM(int *d, int *m);
        }
        else{
            void sumarD(int *d);
        }
        break;

        case 31:
        if(*m == 12){
            void sumarY(int *d, int *m, int *y);
        }
        break;

        default:
        void sumarD(int *d);
        break;

    }

    return 1;
}


void sumarD(int *d){// si no es fin de mes
    *d = *d + 1;
    return;
}


void sumarM(int *d, int *m){// si es fin de mes
    *d = 1; 
    *m = *m + 1;
    return;
}


void sumarY(int *d, int *m, int *y){// si es fin de año
    *d = 1; 
    *m = 1;
    *y = *y + 1;
    return;
}



static int esBisiesto(int anio) {
    int esb=0;
    if((anio%4 == 0 && anio%100!= 0) || anio%400 == 0)
        esb=1;
    return esb;
}


// muy ineficiente y está mal lmao
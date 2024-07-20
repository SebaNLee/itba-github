#include <stdio.h>
#include <stdlib.h>
#include <time.h>


int randDos(int izq, int der){ // asumo que me los da en orden

    int rango = der - izq + 1;

    return izq + rand() % rango;
}

// lindo ejemplo, usé solución alternativa de ChatGPT
// principalmente para que sean iguales las probabilidades que salga un número que otro


// para testear. se agrega srand con time para proporcionarle una semilla
int main(void){

    srand(time(NULL));

    int n = randDos(2, 5);
    
    printf("%d", n);

    return 0;
}
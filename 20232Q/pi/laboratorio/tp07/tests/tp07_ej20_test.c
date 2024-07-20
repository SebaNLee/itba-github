#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef ____ TAlumnos; 

int main(void){

    TAlumnos alumnos = {"Juan", "Pedro", "Martin", ""};
    int notas[] = {1, 4, 10, 2, 10, 11};
    
    char ** apr = aprobados(alumnos, notas);
    if(apr == NULL){
        printf("No hay suficiente memoria!\n");
        return 1;
    }
    assert(!strcmp(alumnos[1], apr[0]) && !strcmp(alumnos[2], apr[1]) && !strcmp(alumnos[3], apr[2]));

    puts("OK!");
    // En este programa no liberamos la memoria que utilizamos. Que ocurre si compilamos con "-fsanitize=address"?
    return 0;
}
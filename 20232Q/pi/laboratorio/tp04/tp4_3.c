#include <stdio.h>

#define VESFERA(r) ((4.0 / 3.0) * (3.1416) * (r) * (r) * (r))

int main(void){


    for(int i = 1; i <= 10; i++){
        printf("Radio: %2d | Volumen: %15.2lf\n", i, VESFERA(i));
    }



    return 0;
}

// cometí un error al poner "4 / 3" en la macro, lo identifica como int
// se tiene que poner 4.0 / 3.0
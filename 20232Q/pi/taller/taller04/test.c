#include <stdio.h>
#include "random.h"

int main(void){

    int n;

    randomize();
    n = randInt(0, 6);

    printf("%d", n);
    



    return 0;
}
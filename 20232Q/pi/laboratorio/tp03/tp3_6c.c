#include <stdio.h>

int main(void){

int var1 = 1, var2;
while (var1 <= 8)
{
var2 += var1;
++ var1;
}

    return 0;
}

// var2 está en basura, se debería inicializar con algún número, 0 por ej
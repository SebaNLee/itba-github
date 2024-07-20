#include <stdio.h>

int main(void){
    


    float a = 0.1;
    if (a == 0.1)
        printf("SON iguales\n");
    else
        printf("NO SON iguales: a vale %g que no es igual a 0.1\n",a);




    return 0;
}

// no se los reconoce como iguales debido a que 0.1 no tiene representación exacta 
// es posible reducir este tipo de error con un double, pero la mejor práctica es usar EPSILON
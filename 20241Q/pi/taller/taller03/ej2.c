#include <math.h>
#include <stdio.h>
#include "getnum.h"

int esAmigo(int input1, int input2);
int sumaDivisores(int input);

int main(void)
{
    int input1, input2, result;

    input1 = getint("Ingrese el primer número: ");
    input2 = getint("Ingrese el segundo número: ");

    result = esAmigo(input1, input2);

    printf("%s son números amigos\n", (result == 1) ? "Sí" : "No");

    return 0;
}

int esAmigo(int input1, int input2)
{
    int count1, count2;

    count1 = sumaDivisores(input1); 
    count2 = sumaDivisores(input2);

    // debug    
    printf("%d, %d\n", count1, count2);
    
    if(count1 == count2)
    {
        return 1;
    }
    else
    {
        return 0;
    }

}


int sumaDivisores(int input)
{
    int suma = 0, mitad;
    
    mitad = input / 2;

    for(int i = 1; i <= mitad; i++)
    {
        if((input % i) == 0)
        {
            suma += i;
        }
    }

    return suma;
}


// no me imprime el resultado bien
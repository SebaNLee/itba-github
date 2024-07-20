#include <stdio.h>

int numerosAmigos(int num1, int num2){

    int rta = 0, mitad1, mitad2, sumaDiv1 = 0, sumaDiv2 = 0, divisor;

    mitad1 = num1 / 2; // guardo la mitad en un int, se pierde el 0.5, pero no me interesa

    for(divisor = 1; divisor <= mitad1 ; divisor++){ // (*)
        if((num1 % divisor) == 0){
            sumaDiv1 += divisor;
        }
    }

    mitad2 = num2 / 2;

    for(divisor = 1; divisor <= mitad2 ; divisor++){ // (*)
        if((num2 % divisor) == 0){
            sumaDiv2 += divisor;
        }
    }

    if((sumaDiv1 == num2) && (sumaDiv2 == num1)){ 
        rta = 1; // esto se pudo haber metido directamente al return
    }

    return rta;
}

// (*) estos for se pudieron haber escrito como funciones
// de esta manera, no hubiese sido necesario el redactar el mismo ciclo dos veces

// para hacerlo más eficiente, se pudo haber agregado un control que la suma no se pase del otro número

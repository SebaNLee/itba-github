#include <stdio.h>

int main(void){

// a)

    int a = getint("Ingrese un número entero:");
    if ( a > 0 )
        printf("Es mayor que cero\n");
    if ( a < 0 )
        printf("Es menor que cero\n");
    if ( a == 0)
        printf("Es igual a cero\n");


    int a = getint("Ingrese un número entero:");
    if(a > 0){
        printf
    }
    else if (a == 0){
        printf
    }
    else{
        printf
    }


// b)

    int a = ( b > 0 ) ? 0 : 1;

    int a = !(b > 0);


// c)

    int a = 0;
    while(1) {
        f(a); // f es una función
        a = a + 1;
        if ( a >= 10 )
            break;
    }

    int a;
    for(a = 0; a <= 10; a++){
        f(a);
    }


// d)

    if ( b > 0 )
    a = 1;
    else
    a = 0;

    a = (b > 0);




// e)

    if ( a > 0 )
    if ( b < 0 )
    c = 10;
    else
    c = 20;
    else
    c = 20;

    if(a > 0 && b < 0){
        c = 10;
    }
    else{
        c = 20;
    }



// f)

    int i = 0;
    int a = 10;
    for ( ; i < 10; ) {
    a += getint("");
    i += 1;
    }
    printf("El valor de a es %d\n", a);

    int i, a;
    for(i = 0, a = 10; i < 10; i++){
        a += getint("");
    }
    printf("");


// g)

    int suma;
    for ( int i=0; i < 100; i++) {
    if ( i==0 )
    suma = getint("Ingrese la base:");
    else
    suma += i;
    }
    printf("La suma es %d\n", a);

    int suma = getint("Ingrese la base: ");
    for(int i = 1; i < 100; i++){
        suma += i;
    }
    printf("");





// h)

    cant = 0;
    for(int i=0; i<dim; i++){
        if(i>=1){
            if(last != v[i]){
                last=v[i];
                cant++;
            }
        }else{
            last=v[i];
            cant++;
        }
    }

    






    return 0;
}
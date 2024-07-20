#include <assert.h>
#include <stdio.h>
#include <math.h>


/* Simplificación de la fracción representada por num y den */
void simplFrac(int *num, int *den);

/* Calcula la suma de dos fracciones representadas por num1 / den1, num2 / den2 
 * Deja el resultado en numS / denS 
 */
void sumarFrac(int num1, int num2, int den1, int den2, int *mumS, int *denS);

/* Devuelve el máximo común divisor de dos números */
int dcm(int num1, int num2);


int main(void) {
    
    int num1, num2, den1, den2, numS, denS;

    printf("SIMPLIFICAR\n");
    num1 = 10; den1 = 15;
    simplFrac(&num1, &den1);
    assert(num1==2 && den1==3);
    simplFrac(&num1, &den1);
    assert(num1==2 && den1==3);

    num1 = 150; den1 = 15;
    simplFrac(&num1, &den1);
    assert(num1==10 && den1==1);
    simplFrac(&num1, &den1);
    assert(num1==10 && den1==1);
    simplFrac(&den1, &num1);
    assert(num1==10 && den1==1);
    num1 = 12*5; den1 = 36*7;
    simplFrac(&num1, &den1);
    assert(num1==5 && den1==21);
    num1 = 0; den1 = 36;
    simplFrac(&num1, &den1);
    assert(num1==0 && den1==1);

    printf("SUMAR\n");
  
    num1 = 150; den1 = 15;
    num2 = 15; den2 = 5;
    sumarFrac(num1, num2, den1, den2, &numS, &denS);
    assert(numS==13 && denS==1);

    num2 = 7; den2 = 4;
    sumarFrac(num1, num2, den1, den2, &numS, &denS);
    assert(numS==47 && denS==4);

    puts("OK!");
    return 0;
}
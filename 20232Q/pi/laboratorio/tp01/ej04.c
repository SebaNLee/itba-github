#include <stdio.h>

int
main (void)
{
    int a, b = 5, c = 5;
    a = b + c;

    printf("Uso var a: %d\n",a);

    return 0;
}



// stdioh a stddio.h
// void con () y no con []
// faltaron ;
// return sin :
// el problema es que no se declara c antes de que es usada como expresión en b = c = 5
// finalmente, tiraba error por no usar la var a, agregué un printf
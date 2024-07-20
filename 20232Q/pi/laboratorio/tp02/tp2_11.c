#include <stdio.h>

int main(){

    char charA, charB, resultado;

    printf("Ingrese dos caracteres: ");
    charA = getchar();
    charB = getchar();

    resultado = (charA > charB) ? charA : charB;
    printf("El ASCII más grande es: %c\n", resultado);

    return 0;
}


/* a) ABCD
** compara solamente los primeros dos, resulta que B es el mayor
** se queda en el buffer C y D
**
*/

/* b) A
**    B
**    C
**    D
** el enter cuenta como caracter ASCII, el A resulta ser el más grande
*/

/* c) A B
** lo mismo para el tabulador, A resulta ser más grande
**
**
*/



#include <stdio.h>

// programa para contar 'a' dentro de un input
// es para ver cómo se comporta getchar en un while != EOF

int main (void)
{
    char a;
    int countA = 0;

    while((a = getchar()) != EOF)
    {
        if(a == 'a')
        {
            countA++;
        }
    }

    printf("Cantidad de 'a's en el texto: %d\n", countA);

    return 0;
}
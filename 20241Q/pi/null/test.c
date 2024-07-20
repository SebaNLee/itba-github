#include <stdio.h>

int main()
{
    int c;
    
    while((c = getchar()) != EOF && c != 'a')
    {
        printf("c: %c\n", c);
    }

    return 0;
}



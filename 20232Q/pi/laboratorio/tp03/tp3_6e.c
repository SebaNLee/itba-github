#include <stdio.h>

int main(void){

char c;
while((c = getchar()) != EOF)
putchar(c);




    return 0;
}

// char c debería ser int, porque puede no abarcar negativos
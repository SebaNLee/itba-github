// no lo completo, muchas condiciones


#include <stdio.h>

int main(void)
{

    int num = -1;

    


    printf("%c\n", (num >= 0 && num <= 15) ? (char) ('A' + num) : (int) num);


    return 0;
}
#include <stdio.h>

int main(void)
{

    int num1 = 1;
    int num2 = 2;
    int num3 = 3;

    printf("%d\n", num1 > num2 ? (num1 > num3 ? num1 : num3) : (num2 > num3) ? num2 : num3);


    return 0;
}



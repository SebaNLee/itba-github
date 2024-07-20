#include <stdio.h>

int a, b;
void local(void);

int main(void)
{
    a = 2;
    b = 3;
    local();
    printf(" a vale : %d\tb vale %d\n", a, b);
    return 0;
}

void local(void)
{
    int a;

    a = -5;
    b = 10;

    return;
}

// falta static en l3
// la var de l17 y l19 son locales a la func local, no afectaría a las var static locales a de l3
// a b si lo afecta func local
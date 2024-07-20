#include <stdio.h>

static int m;
int b;

static void este(void);

int main(void)
{
    m = 2;
    b = 3;
    este();
    printf(" m vale : %d", m);
    printf(" b vale : %d", b);

    return 0;
}

void este(void)
{
    static int m;

    m++;
    b = -3;
    printf("Dentro de este() m vale %d y b vale %d", m, b);

    return;
}

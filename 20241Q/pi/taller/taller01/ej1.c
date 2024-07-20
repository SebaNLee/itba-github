# include <stdio.h>

int main(void)
{
    unsigned char var, var1, var2, var3, var4;

    // uso una máscara para quedarme solamente con los primeros 2 bits
    // 0000 0011 (03)
    unsigned char mask = 0x03;

    // la variable a la derecha
    var = 0xD8;

    var4 = mask & var;
    var = var>>2;

    var3 = mask & var;
    var = var>>2;

    var2 = mask & var;
    var = var>>2;

    var1 = mask & var;
    var = var>>2;


    printf("%x %x %x %x\n", var1, var2, var3, var4);

    return 0;
}






#include <stdio.h>

int main(){
    unsigned char var, var1, var2, var3, var4;
    var = getchar();
    var1 = (var >> 6) & 0x03;
    var2 = (var >> 4) & 0x03;
    var3 = (var >> 2) & 0x03;
    var4 = var & 0x03;

    printf("%c %c %c %c\n%c", var1, var2, var3, var4, var);
    return 0;
}


/* otra solución posible sería:
** var1 = (0xC0 &var)>>6
** var1 = (0x30 &var)>>4
** var1 = (0x0C &var)>>2
** var1 = var &0x03
*/

// también se podría aplicar decalaje para un lado y después para el otro










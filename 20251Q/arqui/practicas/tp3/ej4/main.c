#include <stdio.h>

int siete();

int main() {
    
    printf("%d\n", siete());

    return 0;
}







// nasm -f elf32 siete.asm && gcc -c -m32 main.c && gcc -m32 main.o siete.o -o main && ./main
#include <stdio.h>

unsigned int pid(); // esto está en ASM

int main(int argc, char *argv[])
{
    int mpid = pid();
    printf("Process Id: %d\n", mpid);
    return 0;
}
    // nasm -f elf32 libasm.asm && gcc -c -m32 main.c && gcc -m32 main.o libasm.o -o pid && ./pid
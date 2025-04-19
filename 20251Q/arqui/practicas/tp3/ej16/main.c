#include <stdio.h>

int checkLong(char * str, int num);


int main() {
    char * str = "Hola mundo";
    int num = 8;

    printf("%d\n", checkLong(str, num));
}

// ejecución desde main.c
// nasm -f elf32 checkLong.asm && gcc -c -m32 main.c && gcc -m32 main.o checkLong.o -o main && ./main


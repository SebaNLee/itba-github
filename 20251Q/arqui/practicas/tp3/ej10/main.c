
// voy a hacer que main llame al las otras dos funciones
// lo hago ambos recursivos

#include <stdio.h>

int fiboASM(int n);
int fiboC(int n);


int main(){

    int n = 6;

    int cRes = fiboC(n);
    printf("C: %d\n", cRes);

    int asmRes = fiboASM(n);
    printf("ASM: %d\n", asmRes);

    return 0;
}

// nasm -f elf32 fiboASM.asm && gcc -c -m32 fiboC.c && gcc -c -m32 main.c && gcc -m32 main.o fiboC.o fiboASM.o -o main && ./main
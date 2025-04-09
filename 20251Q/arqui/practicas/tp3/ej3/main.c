
#define STDOUT 1

void puts(int ebx, char * buffer, int size);


int main() {

    puts(STDOUT, "Hola mundo!", 11);

    return 0;
}








// flag para que no use stdlib: -fno-builtin

// nasm -f elf32 puts.asm && gcc -c -m32 -fno-builtin main.c && gcc -m32 main.o puts.o -o main && ./main
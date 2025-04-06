
GLOBAL main
EXTERN hello_world

section .text

main:
    call hello_world

    mov eax, 1
    mov ebx, 0
    int 80h











; nasm -f elf32 main.asm && gcc -c -m32 hello.c && gcc -m32 main.o hello.o -o hello && ./hello
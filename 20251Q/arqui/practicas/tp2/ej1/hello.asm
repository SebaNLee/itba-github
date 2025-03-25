

section .text

global _start

_start:
    ; print msg
    mov eax, 4 ; sys_write
    mov ebx, 1 ; STDOUT
    mov ecx, msg ; msg to print
    mov edx, size ; size
    int 80h

    ; exit
    mov eax, 1 ; sys_exit
    mov ebx, 0 ; return code
    int 80h




section .data
    msg db "Hello World", 10
    size equ $ - msg


; nasm -f elf hello.asm && ld -m elf_i386 hello.o -o hello && ./hello

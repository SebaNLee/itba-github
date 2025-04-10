
GLOBAL _start
EXTERN main


section .text

_start:
    call main ; main retorna en 0, lo recibo en EAX

    mov ebx, eax ; muevo el valor de retorno a EBX
    mov eax, 1 ; syscall_exit
    int 80h















; nasm -f elf32 loader.asm && gcc -c -m32 main.c && gcc -m32 -nostartfiles main.o loader.o -o loader && ./loader

; check de error con:
; echo $?
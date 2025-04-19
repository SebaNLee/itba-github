

GLOBAL _start
EXTERN checkLong
EXTERN print_integer
EXTERN print_newline


section .text

_start:
    push ebp
    mov ebp, esp

    mov ebx, [len]
    mov ecx, msg
    push ebx
    push ecx

    call checkLong ; devuelve en EAX
    call print_integer ; resultado de checkLong ya estáen EAX
    call print_newline
    

    mov esp, ebp
    pop ebp

    mov eax, 1
    mov ebx, 0
    int 80h


section .data
    msg db "Hola Mundo", 0
    len db 8








; ejecución desde main.asm
; nasm -f elf32 checkLong.asm && nasm -f elf32 main.asm && ld -m elf_i386 main.o checkLong.o ../../utils/utils.o -o main && ./main

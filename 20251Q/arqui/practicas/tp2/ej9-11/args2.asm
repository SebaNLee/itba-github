
section .text

GLOBAL _start
EXTERN exit
EXTERN print
EXTERN print_integer
EXTERN print_newline


_start:

    ; ej10
    mov ebx, esp
    add ebx, 4

.cycle:
    cmp dword [ebx], 0 ; si ya imprimió todo, !! comparo con NULL
    je .next ; paro de imprmir

    mov eax, [ebx] ; cargo el puntero del argumento a imprimir en eax
    call print
    call print_newline


    add ebx, 4
    jmp .cycle


.next:

    call exit


; nasm -f elf args2.asm && ld -m elf_i386 args2.o ../../utils/utils.o -o args2 && ./args2 hola como va
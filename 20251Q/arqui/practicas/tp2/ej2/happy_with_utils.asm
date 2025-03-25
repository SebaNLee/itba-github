section .text

GLOBAL _start
EXTERN print

_start:
    mov eax, cadena
    call toupper
    mov ebx, eax
    call print
    mov eax, 1 
    mov ebx, 0
    int 80h	 ;exit

toupper:
    pushad
    pushf
.comienzo:    
    mov cl, [eax]
    cmp cl, 0
    je .fin
    cmp cl, 'a'
    jl .nextletter
    cmp cl, 'z'
    jg .nextletter
    sub cl, 32
    mov [eax], cl
.nextletter:
    inc eax
    jmp .comienzo
.fin: 
    popf
    popad
    ret
section .data
    cadena db "h4ppy c0d1ng",10, 0


; nasm -f elf happy_with_utils.asm && ld -m elf_i386 happy_with_utils.o ../../utils/utils.o -o happy_with_utils && ./happy_with_utils
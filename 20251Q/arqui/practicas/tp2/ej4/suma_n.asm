
section .text

GLOBAL _start
EXTERN print
EXTERN number_to_string

_start:

    mov eax, 7 ; n
    
    mov ebx, 0 ; acumulador de suma

.add_cycle:
    cmp eax, 0 ; si llego a 0, ya no suma
    je .end_cycle ; entonces, salgo del ciclo

    add ebx, eax
    dec eax
    jmp .add_cycle

.end_cycle:
    ; convierto entero a string
    mov eax, placeholder
    mov [eax], ebx
    call number_to_string ; devuelve la representación en string en eax

    call print ; entonces llamo directo a print

    mov eax, newline
    call print

    ; exit
    mov eax, 1
    mov ebx, 0
    int 80h



section .bss
    placeholder resb 4


section .data
    newline db 10



; nasm -f elf suma_n.asm && ld -m elf_i386 suma_n.o ../../utils/utils.o -o suma_n && ./suma_n
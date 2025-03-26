
section .text

GLOBAL _start
EXTERN print
EXTERN number_to_string
EXTERN print_newline

_start:

    mov ebx, 7 ; n

    mov eax, 1 ; acumulador

.factorial:

    mul ebx

    dec ebx

    cmp ebx, 1
    je .exit

    jmp .factorial


.exit:
    ; print
    push dword eax
    mov eax, esp
    call number_to_string
    call print
    call print_newline

    add esp, 4

    ; exit
    mov eax, 1
    mov ebx, 0
    int 80h




; nasm -f elf fact.asm && ld -m elf_i386 fact.o ../../utils/utils.o -o fact && ./fact


EXTERN print
EXTERN print_newline

GLOBAL _start


section .text

_start:
    cpuid
    ; devuelve respuesta a contanear en EBX, EDX, ECX (en ese orden)

    mov [text], ebx
    mov [text + 4], edx
    mov [text + 8], ecx

    mov eax, text
    call print
    call print_newline


    mov eax, 1
    mov ebx, 0
    int 80h


section .data
    text db "123412341234", 0



; nasm -f elf cpuid.asm && ld -m elf_i386 cpuid.o ../../utils/utils.o -o cpuid && ./cpuid
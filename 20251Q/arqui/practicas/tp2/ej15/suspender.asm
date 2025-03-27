
section .text

GLOBAL _start
EXTERN delay_seconds
EXTERN exit



_start:
    mov eax, 3 ; segundos de delay
    call delay_seconds


    call exit






; nasm -f elf suspender.asm && ld -m elf_i386 suspender.o ../../utils/utils.o -o suspender && ./suspender
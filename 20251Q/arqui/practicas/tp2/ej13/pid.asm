


; sys_getpid es 20

section .data

GLOBAL _start
EXTERN print
EXTERN exit
EXTERN number_to_string
EXTERN print_newline


_start:

    mov eax, 20 ; sys_getpid
    int 80h ; devuelve en eax

    mov [placeholder], eax
    mov eax, placeholder

    call number_to_string
    call print
    call print_newline

    call exit


section .bss
    placeholder resb 16

; nasm -f elf pid.asm && ld -m elf_i386 pid.o ../../utils/utils.o -o pid && ./pid & ps ax
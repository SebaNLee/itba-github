

section .text


GLOBAL _start
EXTERN print
EXTERN print_newline
EXTERN exit
EXTERN to_upper


_start:

    ; print input prompt
    mov eax, prompt_input
    call print

    ; get input
    mov eax, 3 ; sys_read
    mov ebx, 0 ; stdin
    mov ecx, buffer
    mov edx, prompt_output_size
    int 80h

    ; ;
    ; mov eax, buffer
    ; call print
    ; call print_newline
    ; ;

    ; to_upper

    mov eax, prompt_output
    call print
    
    mov eax, buffer
    call to_upper
    call print


    call exit



section .bss
    buffer resb 128


section .data
    buffer_size equ 128
    prompt_input db "Input:  ", 0
    prompt_input_size equ $ - prompt_input
    prompt_output db "Output: ", 0
    prompt_output_size equ $ - prompt_output

; nasm -f elf sysread.asm && ld -m elf_i386 sysread.o ../../utils/utils.o -o sysread && ./sysread

section .text

GLOBAL _start
EXTERN print
EXTERN number_to_string
EXTERN print_newline

_start:
    mov ebx, esp ; ebx para recorrer
    mov eax, placeholder

.cycle:
    mov ecx, [ebx] ; copio contenido apuntado por ebx a eax (ecx temp)
    mov [eax], ecx

    call number_to_string
    call print
    call print_newline

    add ebx, 4 ; siguiente
    jmp .cycle


section .bss
    placeholder resb 16


; da segmentation fault, pero imprime bien en output.txt

; nasm -f elf recorre.asm && ld -m elf_i386 recorre.o ../../utils/utils.o -o recorre && ./recorre > ./output.txt
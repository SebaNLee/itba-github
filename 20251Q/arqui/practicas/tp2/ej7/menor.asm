
section .text

GLOBAL _start
EXTERN print
EXTERN number_to_string
EXTERN print_newline


_start:
    mov eax, 0 ; contador para saber cuándo termino
    mov ebx, vector ; ebx para recorrer vector

    ; ecx para menor valor
    ; edx para current
    
    mov ecx, [ebx] ; cargo primer valor

.cycle:
    mov edx, [ebx] ; guardo current en edx
 
    cmp edx, ecx ; comparo actual con mínimo
    jl .new_lower ; si es mínimo salto para cambiar a nuevo mínimo
    
    add ebx, 4 ; !! apunta al siguiente entero (4bytes)
    inc eax

.continue_cycle:
    cmp eax, dim
    je .exit
    jmp .cycle


.new_lower:
    mov ecx, edx ; setteo nuevo mínimo
    jmp .continue_cycle



.exit:
    ; print
    mov eax, placeholder
    mov [eax], ecx
    call number_to_string
    call print
    call print_newline

    mov eax, 1
    mov ebx, 0
    int 80h




section .data
    vector dd 11, 13, 12, 8
    dim equ ($ - vector) / 4 ; lo necesito para saber cuándo termino de recorrer (o se podría hacer con un -1, o similares, al final del vecotor)

section .bss
    placeholder resb 8




; nasm -f elf menor.asm && ld -m elf_i386 menor.o ../../utils/utils.o -o menor && ./menor
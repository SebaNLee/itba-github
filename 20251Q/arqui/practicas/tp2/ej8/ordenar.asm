
section .text

GLOBAL _start
EXTERN print
EXTERN print_newline
EXTERN number_to_string
EXTERN print_vector
EXTERN exit

_start:
    ; voy a usar selection sort
    mov ebx, vector ; ebx para recorrer
    mov ecx, 0 ; ecx contador
    ; edx menor actual

.cycle_outer:
    cmp ecx, dim - 1 ; si ya se recorrió todo el vector
    je .exit ; salto a imprimir
    
    jmp .set_iteration_start; settea ebx al comienzo de la iteración (para no pisar los ya ordenados)

.continue_cycle_outer:
    mov edx, [ebx] ; guardo el primer valor como el menor
    inc ecx ; incremento el contador

.cycle_inner:
    cmp [ebx], edx ; comparo valor menor actual con valor de la iteración
    jl .swap

.continue_cycle_inner:
    add ebx, 4 ; ebx apunta al siguiente (4bytes)
    
    


    cmp ebx, vector + 4 * dim ; si el ciclo actual ya terminó ; !!!! esto anda, pero es muy cuestionable !!!!
    je .cycle_outer ; salto al ciclo externo
    

    jmp .cycle_inner ; si no, continuo en el ciclo interno

    
.swap:    
    mov edx, [ebx]
    ; falta código
    jmp .continue_cycle_inner

.set_iteration_start:
    push edx
    mov edx, 4 ; valor a multiplicar (4bytes, por integer)
    mov ebx, vector ; reseteo ebx
    mov eax, ecx ; cargo contador a eax para multiplicar
    mul edx

    add ebx, eax


    pop edx
    jmp _start.continue_cycle_outer


.exit:
    
    mov eax, vector
    mov ebx, dim
    call print_vector
    call print_newline

    ; exit
    call exit
    


section .data
    vector dd 5, 2, 3, 4, 1
    dim equ ($ - vector) / 4 ; /4 por dd (4bytes)
    debug db "OK", 10 ; debug

section .bss
    placeholder resb 16


; nasm -f elf ordenar.asm && ld -m elf_i386 ordenar.o ../../utils/utils.o -o ordenar && ./ordenar

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
    ; esi dirección del menor actual

.cycle_outer:
    cmp ecx, dim - 1 ; si ya se recorrió todo el vector
    je .exit ; salto a imprimir
    
    jmp .set_iteration_start; settea ebx al comienzo de la iteración (para no pisar los ya ordenados)

.continue_cycle_outer:
    mov edx, [ebx] ; guardo el primer valor como el menor
    mov esi, ebx ; guardo direccion del menor
    inc ecx ; incremento el contador

.cycle_inner:
    cmp [ebx], edx ; comparo valor menor actual con valor de la iteración
    jl .new_lowest

.continue_cycle_inner:
    add ebx, 4 ; ebx apunta al siguiente (4bytes)

    cmp ebx, vector + 4 * dim  ; !!!! esto anda, pero es muy cuestionable !!!!
    jne .cycle_inner ; si no recorrió todo, salto al ciclo interno (sigo recorriendo)
    
    ; si recorrió todo, swappeo
    push eax
    push edx
    
    mov edi, [vector + 4 * (ecx - 1)] ; me guardo el valor del intercambiado
    mov [vector + 4 * (ecx - 1)], edx ; piso el intercambiado con el menor
    mov [esi], edi ; guardo en el menor el intercambiado
    
    pop edx
    pop eax

.skip_swap:
    jmp .cycle_outer ; siguiente iteración

    
.new_lowest:
    mov edx, [ebx] ; guardo valor del menor actual
    mov esi, ebx ; guardo dirección del menor actual

    jmp .continue_cycle_inner

.set_iteration_start: ; esto se pudo haber hecho con [vector + 4 * counter] o similar
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
    vector dd 3, 2, 5, 4, 1
    dim equ ($ - vector) / 4 ; /4 por dd (4bytes)
    debug db "OK", 10 ; debug

section .bss
    placeholder resb 16


; nasm -f elf ordenar.asm && ld -m elf_i386 ordenar.o ../../utils/utils.o -o ordenar && ./ordenar
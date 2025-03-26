

section .text

GLOBAL _start
EXTERN print
EXTERN number_to_string


; para este ej, es mejor ir sumando que ir multiplicando (uso menos registros)
; también se pudo haber hecho con ESP y EBP usando stack
_start:
    ; imprimo k múltiplos de n
    mov eax, 3 ; n
    mov ebx, 10 ; k

    ; obs.: mul op: usa edx:eax = eax * op (edx parte grande, eax parte chica)

    mov edx, eax ; guardo n en edx, para poder usar eax como acumulador y en prints
    mov eax, 0 ; setteo acumulador en 0

.iteration:
    push eax ; me guardo el valor de eax en stack, así lo uso para prints

    mov ecx, eax ; copio valor de eax a ecx
    mov eax, placeholder ; guardo puntero a bytes reservados para casteo
    mov [eax], ecx ; copio a los bytes reservados para el casteo el valor del acumulador (copia en ecx)

    call number_to_string ; llamo casteo
    call print ; imprimo

    mov eax, newline ; guardo puntero para nueva línea
    call print ; imprimo

    dec ebx ; siguiente del contador

    cmp ebx, 0 ; si es fin
    je .exit ; salir

    pop eax ; recupero el valor de eax del stack

    add eax, edx ; sumo 
    
    jmp .iteration ; caso en el que tengo que seguir

.exit:
    mov eax, 1
    mov ebx, 0
    int 80h



section .bss
    placeholder resb 8

section .data
    newline db 10




; nasm -f elf multiplos.asm && ld -m elf_i386 multiplos.o ../../utils/utils.o -o multiplos && ./multiplos
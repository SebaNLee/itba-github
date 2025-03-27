
; fork es 2
; devuelve por eax
; si devuelve 0 es hijo
; si es mayor a 0 es padre

section .text

GLOBAL _start
EXTERN print
EXTERN print_newline
EXTERN exit


_start:
    ; llamo a sys_fork
    mov eax, 2
    int 80h

    cmp eax, 0
    je .son

    cmp eax, 0
    jg .father

    jmp .error

.son:
    mov eax, son
    call print
    jmp exit

.father:

;     ; delay
;     mov eax, 4000000000
; .ciclo:
;     cmp eax, 0
;     je .continue
;     dec eax
;     jmp .ciclo

; .continue:


    mov eax, father
    call print
    jmp exit

.error:
    mov eax, error
    call print
    jmp exit

.exit:
    call exit


section .data
    son db "Soy el hijo", 10, 0
    father db "Soy el padre", 10, 0
    error db "Error", 10, 0



; nasm -f elf fork.asm && ld -m elf_i386 fork.o ../../utils/utils.o -o fork && ./fork

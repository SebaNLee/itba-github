
section .text

global _start

_start:
    mov eax, msg
    call toupper ; devuelve el string en eax
    call print
    ; exit
    mov eax, 1
    mov ebx, 0
    int 80h


toupper:
    pushad ; guarda registros en pila
    pushf ; guarda flags en pila

.cycle:
    mov cl, [eax] ; guardo el caracter apuntado por eax
    cmp cl, 0
    je .exit ; si son iguales, llegó al final
    cmp cl, 'a'
    jl .pass ; si es menor que 'a' no interesa
    cmp cl, 'z'
    jg .pass ; ídem mayor 'z'
    sub cl, distance
    mov [eax], cl

.pass:
    inc eax ; points to next char
    jmp .cycle

.exit:
    popf
    popad
    ret


print:
    mov ecx, eax
    mov eax, 4
    mov ebx, 1
    mov edx, size
    int 80h
    ret


section .data
    msg db "h4ppy c0d1ng", 10, 0 ; termina en 0 por convención
    size equ $ - msg
    distance equ 'a' - 'A' ; distancia a restar ASCII



; básicamente, se está usando cl de variable temporal
; se extraen de a un caracter desde eax y se va modificando si es que se debe

; nasm -f elf happy.asm && ld -m elf_i386 happy.o -o happy && ./happy
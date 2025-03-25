
; FUNCIÓN PRINT GENÉRICA
; RECIBE EL STRING A IMRPIMIR EN EAX


section .text

GLOBAL print

print:
    pushad ; backup de registros
    pushf ; backup de flags

    mov ecx, eax ; copio string a imprimir a ecx, porque strlen devuelve con eax
    mov ebx, eax ; guardo en ebx el puntero iterador para strlen

    ; calculo largo del string
    call strlen

    ; imprimo
    mov edx, eax
    mov eax, 4
    mov ebx, 1
    int 80h

    popf ; recupero flags
    popad ; recupero registros
    ret


strlen:
    ; eax lo uso así nomás y lo uso para devover
    ; y guardo ecx (que tiene el sting a imprimir) y lo recupero después
    ; ecx lo uso de iterador
    push ecx
    pushf

    mov ecx, 0 ; inicializo contador

.loop:
    mov al, [ebx]
    cmp al, 0
    jz .end
    inc ecx
    inc ebx
    jmp .loop

.end:
    mov eax, ecx ; devuelvo strlen con eax

    popf
    pop ecx
    ret


; solo compilación
; nasm -f elf print.asm 
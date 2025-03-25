section .text

GLOBAL _start
EXTERN print  ; Función externa para imprimir la cadena

_start:
    mov eax, number  ; Cargamos el número a convertir en EAX
    mov edi, placeholder  ; Buffer donde guardaremos la cadena

    ; Convertir número a string
    call convert

    ; Imprimir el string convertido
    call print

    ; Salir del programa
    mov eax, 1
    xor ebx, ebx
    int 80h

convert:
    push ebp
    mov ebp, esp

    mov ecx, 10       ; Divisor = 10 (para obtener cada dígito)
    mov esi, edi      ; Guardamos la dirección original del buffer en ESI

.cycle:
    mov edx, 0        ; Limpiar EDX antes de la división
    div ecx           ; EAX = cociente, EDX = resto (EDX = número % 10)
    
    add dl, '0'       ; Convertir número a ASCII (sumar '0')
    mov [edi], dl     ; Guardar el dígito en el buffer
    inc edi           ; Avanzar en el buffer

    test eax, eax     ; ¿Ya terminamos? (EAX == 0)
    jnz .cycle        ; Si no, seguimos dividiendo

    mov byte [edi], 0 ; Agregar terminador NULL ('\0') al final

    ; Invertir la cadena en el buffer (porque se guardó en orden inverso)
    mov edi, esi      ; Volver al inicio del buffer
    call reverse

    pop ebp
    ret

reverse:
    push ebp
    mov ebp, esp

    mov edx, edi      ; Guardamos la dirección de inicio
    mov esi, edi      ; Inicio
    mov edi, esi      ; Fin
.loop:
    dec edi           ; Retrocede para llegar al último carácter
    cmp edi, esi      ; Si ya están en la misma posición, salir
    jle .done

    ; Intercambiar caracteres
    mov al, [esi]
    mov ah, [edi]
    mov [esi], ah
    mov [edi], al

    inc esi           ; Avanzar desde el inicio
    jmp .loop         ; Seguir hasta que se inviertan todos

.done:
    pop ebp
    ret

section .data
    number dd 120  ; Número a convertir

section .bss
    placeholder resb 16  ; Buffer para almacenar el número convertido


; nasm -f elf reff.asm && ld -m elf_i386 reff.o ../../utils/print.o -o reff && ./reff

section .text

GLOBAL _start
EXTERN print

_start:
    ; parámetros para convert
    mov eax, placeholder
    mov ebx, 123 ; defino acá el número
    
    ; convert, returns the converted string in eax
    call convert

    ; convert me lo devuelve en edi, entonces lo paso a eax
    mov eax, edi

    ; print, prints the content in eax
    call print

    ; print newline
    mov eax, newline
    call print

    ; exit
    mov eax, 1
    mov ebx, 0
    int 80h


convert:
    push ebp
    mov ebp, esp

    mov edi, eax ; guardo placeholder en para conservar edi (necesito eax para dividir)
    mov eax, ebx ; guardo el número a dividir (y dividendo) en eax
    mov ebx, edi ; copio placeholder en ebx, para recorrer
    

.cycle:
    ; consigo el resto de / 10
    mov edx, 0 ; reseteo resto a 0
    mov ecx, 10 ; setteo divisor
    div ecx ; eax = eax / 10

    mov cl, dl ; me guardo el resto en cl (desde dl, último byte de edx)
    add cl, '0' ; convertir a ASCII
    mov [ebx], cl
    inc ebx

    cmp eax, 0
    jnz .cycle

    ; ya terminó de leer todo
    mov byte [ebx], 0; agrego \0 al final

    ; ==================
    ; mov eax, edi
    ; mov esp, ebp
    ; pop ebp
    ; ret
    ; ============

    call invert
    

    ; return
    mov esp, ebp
    pop ebp
    ret


invert:
    push ebp
    mov ebp, esp

    ; primero, tengo que encontrar el final
    mov esi, edi ; esi lo uso para recorrer desde el principio
    mov ebx, edi ; ebs lo uso para recorrer desde el final

.find_end:
    cmp byte [ebx], 0 ; comparo si es '\0'
    je .end_found ; si encuentro, salgo de loop
    inc ebx ; si no, sigo buscando
    jmp .find_end

.end_found:
    dec ebx ; porque está en '\0', que no se debe swappear

    ; ahora invierto el string
.reverse_loop:
    cmp esi, ebx ; comparo si son iguales
    jge .end ; fin si es mayor o igual (porque ebx estaba al final)

    ; si no es final, intercambio
    mov al, [esi]
    mov ah, [ebx]
    mov [esi], ah
    mov [ebx], al

    inc esi
    dec ebx
    jmp .reverse_loop


.end:
    mov esp, ebp
    pop ebp
    ret


section .bss
    placeholder resb 16

section .data
    newline db "", 10



; !! obs.: en este caso, era totalmente innecesario hacer push y pop de ESP

; nasm -f elf num2str.asm && ld -m elf_i386 num2str.o ../../utils/print.o -o num2str && ./num2str
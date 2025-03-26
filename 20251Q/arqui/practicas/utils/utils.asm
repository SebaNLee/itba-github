


section .text

GLOBAL print
GLOBAL strlen
GLOBAL number_to_string
GLOBAL print_newline


; ===============================================================
; print: 
;   - imprime por pantalla el string recibido por EAX, se encarga de calcularlo también
;   - guarda los registros y flags al iniciar y los deja intactos al terminar
; parámetros:
;   - EAX: string a imprimir, terminado con \0
; ===============================================================
print:
    pushad ; backup de registros
    pushf ; backup de flags

    mov ecx, eax ; copio string a imprimir a ecx, porque strlen devuelve con eax

    ; calculo largo del string
    ; print es llamado con eax, strlen también con eax
    call strlen

    ; imprimo
    mov edx, eax
    mov eax, 4
    mov ebx, 1
    int 80h

    popf ; recupero flags
    popad ; recupero registros
    ret


; ===============================================================
; strlen: 
;   - calcula el largo de un string terminado en \0 y lo retorna en EAX
;   - guarda los registros y flags al iniciar y los deja intactos al terminar
; parámetros:
;   - EAX: string a imprimir, terminado con 0
; retorna:
;   - EAX: entero, largo del string
; ===============================================================
strlen:
    ; eax lo uso así nomás y lo uso para devover
    ; y guardo ecx (que tiene el sting a imprimir) y lo recupero después
    ; ecx lo uso de iterador
    push ecx
    push ebx
    pushf

    mov ebx, eax

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
    pop ebx
    pop ecx
    ret


; ===============================================================
; number_to_string: 
;   - recibe un entero positivo y retorna su representación en string terminado con \0
;   - es dinámico, se puede ejecutar con cualquier .bss resb, pero recorre 2 veces por string inverso
; parámetros:
;   - EAX: dirección de memoria del placeholder con el entero positivo a convertir (.bss resb, recomiendo 32)
; retorna:
;   - EAX: dirección de memoria del placeholder, con la representación del entero positivo en string terminado con \0 (sobreescribe)
; obs.:
;   resb determina los bytes guardados y, por ende, los caracteres a poder guardarse, aunque de enteros se pueda guardar más
; ===============================================================
number_to_string:

    push edi ; me guardo esb por si las dudas, lo voy a usar para guardar
    push ebx ; me guardo ebx, lo voy a usar para recorrer
    push ecx ; me guardo ecx, lo necesito para el divisor
    push edx ; me guardo edx, lo necesito para el resto de DIV y convertir a ASCII
    pushf

    mov edi, eax ; me guardo copia para retornarlo después (eax lo necesito para operaciones)
    mov ebx, edi ; ebx lo voy a usar para recorrer
    mov eax, dword [eax]  ; carga el número a eax

    mov ecx, 10 ; divisor
    
.loop:
    mov edx, 0 ; reseteo el resto a 0
    div ecx ; divido el número a / 10

    add dl, '0' ; converierto el resto (último byte de edx) a ASCII
    mov [ebx], dl ; lo guardo en placeholder
    inc ebx ; apunto a siguiente posición a escribir

    cmp eax, 0 ; si siguen habiendo números
    jnz .loop ; siguiente iteración

    ; terminado la lectura (llegué a 0 dividiendo)
    mov byte [ebx], 0 ; agrego el \0 al final

    ; ahora invierto el string para que quede bien
    mov eax, edi; eax lo uso para recorrer desde principio
    mov ebx, edi; ebx lo uso para reocrrer desde final


.find_end_loop:
    cmp byte [ebx], 0 ; comparo si es \0
    je .end_found
    inc ebx
    jmp .find_end_loop

.end_found:
    dec ebx ; porque no quiero swappear el \0

.invert_loop:
    cmp eax, ebx ; comparo las direcciones de principio y fin
    jge .exit ; si es mayor o igual, me pasé o estoy al límite, entonces salgo

    ; si no es final, intercambio
    mov cl, [eax]
    mov dl, [ebx]
    mov [eax], dl
    mov [ebx], cl

    inc eax
    dec ebx
    jmp .invert_loop


.exit:
    mov eax, edi

    popf
    pop edx
    pop ecx
    pop ebx
    pop edi
    ret 


; ===============================================================
; print_newline: 
;   - imprime por pantalla '\n'
; ===============================================================
print_newline:
    pushad ; backup de registros
    pushf ; backup de flags

    ; '\n' en stack
    push dword 10 ; 4 bytes, es dword

    ; imprimo
    mov eax, 4
    mov ebx, 1
    mov ecx, esp
    mov edx, 1
    int 80h

    ; recupero stack
    add esp, 4 ; del dword de '\n'

    popf ; recupero flags
    popad ; recupero registros
    ret


; solo compilación:
; nasm -f elf utils.asm 
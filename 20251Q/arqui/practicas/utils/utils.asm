


section .text

GLOBAL print
GLOBAL strlen
GLOBAL number_to_string
GLOBAL print_newline
GLOBAL print_vector
GLOBAL exit
GLOBAL print_integer
GLOBAL delay_seconds




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
;   - es dinámico, se puede ejecutar con cualquier .bss resb o stack, pero recorre 2 veces por string inverso
; parámetros:
;   - EAX: dirección de memoria del placeholder con el entero positivo a convertir (recomiendo .bss resb 32)
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

; ===============================================================
; print_vector: 
;   - imprime por pantalla un vector de dwords
; parámetros:
;   - EAX: dirección de memoria al vector
;   - EBX: entero dimensión del vector
; ===============================================================
print_vector:

    push ecx
    push edx

    mov ecx, 0 ; ecx contador
    mov edx, eax ; muevo dir de mem de veector a edx, eax lo uso para imprimir

.cycle:
    cmp ecx, ebx ; comparo contador con dimension
    je .exit ; salgo si llegué al final

    ; imprimo entero
    push dword [edx] ; lo uso para number_to_string
    mov eax, esp
    call number_to_string
    call print
    add esp, 4 ; recupero el espacio usado
    
    push dword 32 ; 32 es ' ' en ASCII
    mov eax, esp
    call print
    add esp, 4

    inc ecx
    add edx, 4 ; el siguiente son 4bytes por enteros
    jmp .cycle

.exit:
    
    ;add esp, 4 ; recupero el espacio del espaciado
    pop edx
    pop ecx
    ret


; ===============================================================
; exit: 
;   - int80 y sale con código 0
; ===============================================================
exit:
    mov eax, 1
    mov ebx, 0
    int 80h


; ===============================================================
; print_integer: 
;   - recibe un entero positivo y lo imprime
; parámetros:
;   - EAX: dword entero
; ===============================================================
print_integer:
    push ebp ; stack frame
    mov ebp, esp

    push dword eax ; guardo el entero en el stack
    mov eax, esp ; guardo en eax la dirección donde está guardado el entero en el stack
    call number_to_string
    call print

    add esp, 4 ; recupero memoria del stack

    mov esp, ebp
    pop ebp
    ret


; ===============================================================
; delay_seconds: 
;   - delay de cantidad en segundos
; parámetros:
;   - EAX: entero cantidad de segundos
; ===============================================================
delay_seconds:
    ; systemc call, sys_time (13)
    ; guarda el tiempo actual en entero a la dirección apuntada por ebx
    ; eax también va a guardar la dirección de ebx (donde está guardado el tiempo)

    mov ecx, eax ; copio el delay a ecx

    ; primero calculo objetivo
    mov eax, 13
    mov ebx, time_temp
    int 80h ; devuelve el tiempo como entero en eax

    ; calculo tiempo de finalización
    add [time_temp], ecx ; le sumo el delay pedido
    mov esi, [time_temp] ; guardo el tiempo de finalización en ebx

.check:
    mov eax, 13 ; !! tengo que reponer, porque modifica eax al retornar
    int 80h ; !! eax = 13 y ebx = time_temp (ebx no se modifica durante ejecución)

    cmp [time_temp], esi
    jge .exit
    
    jmp .check 

.exit:
    mov eax, ecx ; dejo eax intacto (why not)
    ret


section .bss
    time_temp resb 4 ; delay_seconds



; solo compilación:
; nasm -f elf utils.asm 
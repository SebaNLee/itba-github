

GLOBAL checkLong
EXTERN puts

section .text

; devuelve diferencia entre longitud del str y num
; int checkLong(char * str, int num)
checkLong:
    push ebp
    mov ebp, esp
    push ebx
    push esi
    push edi

    mov ebx, [ebp + 8] ; str
    mov ecx, [ebp + 12] ; num
    mov edx, 0 ; EDX lo uso de contador

.cycle:
    mov al, [ebx]
    cmp al, 0
    je .end


    inc edx

    inc ebx
    jmp .cycle

.end:
    ; comparo y retorno en EAX
    mov eax, edx
    sub eax, ecx

    pop edi
    pop esi
    pop ebx
    mov esp, ebp
    pop ebp
    ret
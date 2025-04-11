

GLOBAL fiboASM


section .text

; recordar:
; EBX, ESI, EDI, ESP, EBP se tienen que mantener intactos

; int fiboASM(int n);
fiboASM:
    push ebp
    mov ebp, esp
    push ecx

    mov eax, [ebp + 8] ; obtengo n

    ; si es caso base
    cmp eax, 1 ; si n <= 1
    jle .basecase 

    ; si no es caso base
    ; obtengo fibo(n - 1)
    dec eax
    push eax
    call fiboASM
    mov ecx, eax
    pop eax

    ; obtengo fibo(n - 2)
    dec eax
    push eax
    call fiboASM
    add ecx, eax
    pop eax

    ; obtengo fibo(n - 1) + fibo(n - 2)
    mov eax, ecx

    jmp .exit

.basecase:
    mov eax, 1 ; retorno caso base

.exit:
    pop ecx
    mov esp, ebp
    pop ebp

    ret

; !! no me estaba andando porque pisaba ECX entre ejecuciones
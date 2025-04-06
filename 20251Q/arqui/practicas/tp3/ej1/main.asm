
GLOBAL main
EXTERN printf


section .rodata
    fmt db "Cantidad de argumentos: %d", 10, 0
    fmt2 db "Argumentos:", 10, 0
    format_specifier db "%s", 10, 0


section .text

main:
    push ebp
    mov ebp, esp

    push dword [ebp+8]
    push fmt
    call printf
    add esp, 2*4

    ; imrpimo título 

    push fmt2
    call printf
    add esp, 4

    ; preparo recorrido
    mov ebx, [ebp + 8] ; ebx de contador, argc
    mov esi, [ebp + 12] ; dirección del array de argumentos, argv

.cycle:
    cmp ebx, 0 ; si ya se llegó al final
    je .end ; salto a fin

    ;mov eax, [esi + ecx * 4] ; me guardo la dirección que apunta al i-ésimo argv
    mov eax, [esi]
    add esi, 4
    push eax
    push format_specifier
    call printf
    add esp, 8

    dec ebx
    jmp .cycle


.end:
    mov eax, 0
    
    mov esp, ebp
    pop ebp
    ret



; es:
; int main(int argc, char * argv[])
; 0 EBP
; 4 dirección de retorno (main)
; 8 ARGC
; 12 ARGV

; nasm -f elf32 main.asm && gcc -m32 main.o -o main && ./main hola como va

GLOBAL puts

section .text

puts:
    push ebp
    mov ebp, esp

    mov eax, 4
    mov ebx, [ebp + 8]
    mov ecx, [ebp + 12]
    mov edx, [ebp + 16]
    int 80h

    mov eax, 4
    mov ebx, 1
    mov ecx, new_line
    mov edx, 1
    int 80h

    mov esp, ebp
    pop ebp
    ret

section .data
    new_line db 10

section .text

GLOBAL _start
EXTERN exit
EXTERN print
EXTERN print_integer
EXTERN print_newline


_start:

    ; ej9
    mov eax, [esp] ; guardo la cantidad de args en eax
    call print_integer
    call print_newline
    call print_newline


    ; ej10
    mov ebx, 1 ; ebx lo uso como contador (empiezo de +4bytes)
    mov edx, [esp] ; cota final
    inc edx ; icremento, porque no imprimo el primero (está la cant de args) y no quiero omitir el último

.cycle:
    cmp ebx, edx ; si ya imprimió todo
    je .next ; paro de imprmir

    mov eax, [esp + ebx * 4] ; cargo el puntero del argumento a imprimir en eax
    call print
    call print_newline


    inc ebx
    jmp .cycle


.next:
    call print_newline

    ; ej11
    mov ebx, [esp] ; guardo temporalmente el inicio del stack en ebx
    mov ebx, [esp + 4 * (ebx + 2)] ; guardo en edx el comienzo de las variables de entorno (+2 por: cant de args y null separador (ver tp2 hoja4))

.cycle2:
    cmp dword [ebx], 0 ; si está apuntando a null, llegó al final
    je .exit ; entonces salgo

    mov eax, ebx
    call print
    call print_newline

    add ebx, 4

    jmp .cycle2


.exit:
    call exit


; nasm -f elf args.asm && ld -m elf_i386 args.o ../../utils/utils.o -o args && ./args hola como va > ./output.txt
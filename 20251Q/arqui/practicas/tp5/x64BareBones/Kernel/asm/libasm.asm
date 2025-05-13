GLOBAL cpuVendor

; para Ej3
GLOBAL _seconds
GLOBAL _minutes
GLOBAL _hours

; para Ej4
GLOBAL kbPooling

section .text
	
cpuVendor:
	push rbp
	mov rbp, rsp

	push rbx

	mov rax, 0
	cpuid


	mov [rdi], ebx
	mov [rdi + 4], edx
	mov [rdi + 8], ecx

	mov byte [rdi+13], 0

	mov rax, rdi

	pop rbx

	mov rsp, rbp
	pop rbp
	ret


; Ej3
_seconds:
	mov al, 0 ; 0 para segundos
	out 70h, al ; 70h lo que quiero
	in al, 71h ; 71h donde está lo que quiero
	ret

_minutes:
	mov al, 2 ; 2 para minutos
	out 70h, al
	in al, 71h
	ret

_hours:
	mov al, 4 ; 4 para horas
	out 70h, al
	in al, 71h
	ret


; Ej4
; !! esto se ejecuta por cada tecla presionada y soltada
; y lo que devuelve no es ASCII, sino el scancode (QWERTY)
kbPooling:
	mov al, 0 ; reseteo al

.loop:
	in al, 64h ; cargo los 8 bits del puerto de información 
	and al, 01h ; solo me interesa el último bit (me dice si hay para leer)
	cmp al, 0 ; si no hay nada para leer
	je .loop ; entonces espero

	in al, 60h
	ret


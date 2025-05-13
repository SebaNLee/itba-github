GLOBAL cpuVendor
; para Ej3
GLOBAL _seconds
GLOBAL _minutes
GLOBAL _hours

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




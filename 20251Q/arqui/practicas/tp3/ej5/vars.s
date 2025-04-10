	.file	"vars.c"
	.intel_syntax noprefix
	.text
	.globl	var3
	.bss
	.align 4
	.type	var3, @object
	.size	var3, 4
var3:
	.zero	4
	.data
	.align 4
	.type	var6, @object
	.size	var6, 4
var6:
	.long	11
	.text
	.globl	main
	.type	main, @function
main:
	push	ebp
	mov	ebp, esp
	push	ebx
	and	esp, -16
	sub	esp, 16
	call	__x86.get_pc_thunk.bx
	add	ebx, OFFSET FLAT:_GLOBAL_OFFSET_TABLE_
	call	separador
	mov	DWORD PTR 12[esp], 21
	call	separador
	mov	DWORD PTR var3@GOTOFF[ebx], 22
	call	separador
	call	separador
	mov	eax, DWORD PTR var5@GOT[ebx]
	mov	DWORD PTR [eax], 10
	call	separador
	mov	DWORD PTR var6@GOTOFF[ebx], 24
	call	separador
	mov	eax, 0
	mov	ebx, DWORD PTR -4[ebp]
	leave
	ret
	.size	main, .-main
	.globl	separador
	.type	separador, @function
separador:
	push	ebp
	mov	ebp, esp
	call	__x86.get_pc_thunk.ax
	add	eax, OFFSET FLAT:_GLOBAL_OFFSET_TABLE_
	nop
	pop	ebp
	ret
	.size	separador, .-separador
	.data
	.align 4
	.type	var4.0, @object
	.size	var4.0, 4
var4.0:
	.long	23
	.section	.text.__x86.get_pc_thunk.ax,"axG",@progbits,__x86.get_pc_thunk.ax,comdat
	.globl	__x86.get_pc_thunk.ax
	.hidden	__x86.get_pc_thunk.ax
	.type	__x86.get_pc_thunk.ax, @function
__x86.get_pc_thunk.ax:
	mov	eax, DWORD PTR [esp]
	ret
	.section	.text.__x86.get_pc_thunk.bx,"axG",@progbits,__x86.get_pc_thunk.bx,comdat
	.globl	__x86.get_pc_thunk.bx
	.hidden	__x86.get_pc_thunk.bx
	.type	__x86.get_pc_thunk.bx, @function
__x86.get_pc_thunk.bx:
	mov	ebx, DWORD PTR [esp]
	ret
	.ident	"GCC: (Ubuntu 11.4.0-1ubuntu1~22.04) 11.4.0"
	.section	.note.GNU-stack,"",@progbits

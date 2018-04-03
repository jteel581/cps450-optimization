	.file	"stdlib.c"
	.text
	.globl	writeint
	.type	writeint, @function
writeint:
.LFB0:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	pushl	%ebx
	subl	$68, %esp
	.cfi_offset 3, -12
	movl	%gs:20, %eax
	movl	%eax, -12(%ebp)
	xorl	%eax, %eax
	movl	$2608, -32(%ebp)
	movl	$0, -28(%ebp)
	movl	$0, -24(%ebp)
	movl	$0, -20(%ebp)
	movl	$0, -16(%ebp)
	leal	-52(%ebp), %eax
	movl	%eax, -60(%ebp)
	leal	-32(%ebp), %eax
	movl	%eax, -56(%ebp)
	cmpl	$0, 8(%ebp)
	jns	.L2
	movl	-56(%ebp), %eax
	leal	1(%eax), %edx
	movl	%edx, -56(%ebp)
	movb	$45, (%eax)
	negl	8(%ebp)
.L2:
	cmpl	$0, 8(%ebp)
	jle	.L3
	jmp	.L4
.L5:
	movl	-60(%ebp), %ebx
	leal	1(%ebx), %eax
	movl	%eax, -60(%ebp)
	movl	8(%ebp), %ecx
	movl	$1717986919, %edx
	movl	%ecx, %eax
	imull	%edx
	sarl	$2, %edx
	movl	%ecx, %eax
	sarl	$31, %eax
	subl	%eax, %edx
	movl	%edx, %eax
	sall	$2, %eax
	addl	%edx, %eax
	addl	%eax, %eax
	subl	%eax, %ecx
	movl	%ecx, %edx
	movl	%edx, %eax
	addl	$48, %eax
	movb	%al, (%ebx)
	movl	8(%ebp), %ecx
	movl	$1717986919, %edx
	movl	%ecx, %eax
	imull	%edx
	sarl	$2, %edx
	movl	%ecx, %eax
	sarl	$31, %eax
	subl	%eax, %edx
	movl	%edx, %eax
	movl	%eax, 8(%ebp)
.L4:
	cmpl	$0, 8(%ebp)
	jg	.L5
	subl	$1, -60(%ebp)
	jmp	.L6
.L7:
	movl	-56(%ebp), %eax
	leal	1(%eax), %edx
	movl	%edx, -56(%ebp)
	movl	-60(%ebp), %edx
	leal	-1(%edx), %ecx
	movl	%ecx, -60(%ebp)
	movzbl	(%edx), %edx
	movb	%dl, (%eax)
.L6:
	leal	-52(%ebp), %eax
	cmpl	%eax, -60(%ebp)
	ja	.L7
	movl	-56(%ebp), %eax
	leal	1(%eax), %edx
	movl	%edx, -56(%ebp)
	movl	-60(%ebp), %edx
	movzbl	(%edx), %edx
	movb	%dl, (%eax)
	movl	-56(%ebp), %eax
	leal	1(%eax), %edx
	movl	%edx, -56(%ebp)
	movb	$10, (%eax)
	movl	-56(%ebp), %eax
	leal	1(%eax), %edx
	movl	%edx, -56(%ebp)
	movb	$0, (%eax)
	jmp	.L8
.L3:
	leal	-32(%ebp), %eax
	addl	$3, %eax
	movl	%eax, -56(%ebp)
.L8:
	movl	-56(%ebp), %edx
	leal	-32(%ebp), %eax
	subl	%eax, %edx
	movl	%edx, %eax
	subl	$1, %eax
	subl	$4, %esp
	pushl	%eax
	leal	-32(%ebp), %eax
	pushl	%eax
	pushl	$1
	call	write
	addl	$16, %esp
	nop
	movl	-12(%ebp), %eax
	xorl	%gs:20, %eax
	je	.L9
	call	__stack_chk_fail
.L9:
	movl	-4(%ebp), %ebx
	leave
	.cfi_restore 5
	.cfi_restore 3
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
.LFE0:
	.size	writeint, .-writeint
	.globl	readint
	.type	readint, @function
readint:
.LFB1:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$72, %esp
	movl	%gs:20, %eax
	movl	%eax, -12(%ebp)
	xorl	%eax, %eax
	movl	$20, -44(%ebp)
	movl	$0, -40(%ebp)
	subl	$4, %esp
	pushl	-44(%ebp)
	leal	-32(%ebp), %eax
	pushl	%eax
	pushl	$0
	call	read
	addl	$16, %esp
	movl	%eax, -40(%ebp)
	movl	$0, -60(%ebp)
	movl	$1, -56(%ebp)
	movzbl	-32(%ebp), %eax
	movb	%al, -61(%ebp)
	movl	$1, -48(%ebp)
	cmpb	$45, -61(%ebp)
	jne	.L11
	movl	$0, -48(%ebp)
.L11:
	movl	-40(%ebp), %eax
	subl	$2, %eax
	movl	%eax, -52(%ebp)
	jmp	.L12
.L15:
	leal	-32(%ebp), %edx
	movl	-52(%ebp), %eax
	addl	%edx, %eax
	movzbl	(%eax), %eax
	movsbl	%al, %eax
	subl	$48, %eax
	movl	%eax, -36(%ebp)
	movl	-36(%ebp), %eax
	imull	-56(%ebp), %eax
	addl	%eax, -60(%ebp)
	cmpl	$0, -48(%ebp)
	jne	.L13
	cmpl	$1, -52(%ebp)
	jne	.L13
	negl	-60(%ebp)
	jmp	.L14
.L13:
	subl	$1, -52(%ebp)
	movl	-56(%ebp), %edx
	movl	%edx, %eax
	sall	$2, %eax
	addl	%edx, %eax
	addl	%eax, %eax
	movl	%eax, -56(%ebp)
.L12:
	cmpl	$0, -52(%ebp)
	jns	.L15
.L14:
	movl	-60(%ebp), %eax
	movl	-12(%ebp), %ecx
	xorl	%gs:20, %ecx
	je	.L17
	call	__stack_chk_fail
.L17:
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
.LFE1:
	.size	readint, .-readint
	.globl	multiply
	.type	multiply, @function
multiply:
.LFB2:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	imull	12(%ebp), %eax
	movl	%eax, -20(%ebp)
	fildl	-20(%ebp)
	fstpl	-8(%ebp)
	fldl	-8(%ebp)
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
.LFE2:
	.size	multiply, .-multiply
	.globl	divide
	.type	divide, @function
divide:
.LFB3:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	cltd
	idivl	12(%ebp)
	movl	%eax, -20(%ebp)
	fildl	-20(%ebp)
	fstpl	-8(%ebp)
	fldl	-8(%ebp)
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
.LFE3:
	.size	divide, .-divide
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.9) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits

	.file	"libTest.c"
	.text
	.globl	writeint
	.type	writeint, @function
writeint:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$96, %rsp
	movl	%edi, -84(%rbp)
	movq	%fs:40, %rax
	movq	%rax, -8(%rbp)
	xorl	%eax, %eax
	movq	$2608, -32(%rbp)
	movq	$0, -24(%rbp)
	movl	$0, -16(%rbp)
	leaq	-64(%rbp), %rax
	movq	%rax, -80(%rbp)
	leaq	-32(%rbp), %rax
	movq	%rax, -72(%rbp)
	cmpl	$0, -84(%rbp)
	jns	.L2
	movq	-72(%rbp), %rax
	leaq	1(%rax), %rdx
	movq	%rdx, -72(%rbp)
	movb	$45, (%rax)
	negl	-84(%rbp)
.L2:
	cmpl	$0, -84(%rbp)
	jle	.L3
	jmp	.L4
.L5:
	movq	-80(%rbp), %rsi
	leaq	1(%rsi), %rax
	movq	%rax, -80(%rbp)
	movl	-84(%rbp), %ecx
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
	movb	%al, (%rsi)
	movl	-84(%rbp), %ecx
	movl	$1717986919, %edx
	movl	%ecx, %eax
	imull	%edx
	sarl	$2, %edx
	movl	%ecx, %eax
	sarl	$31, %eax
	subl	%eax, %edx
	movl	%edx, %eax
	movl	%eax, -84(%rbp)
.L4:
	cmpl	$0, -84(%rbp)
	jg	.L5
	subq	$1, -80(%rbp)
	jmp	.L6
.L7:
	movq	-72(%rbp), %rax
	leaq	1(%rax), %rdx
	movq	%rdx, -72(%rbp)
	movq	-80(%rbp), %rdx
	leaq	-1(%rdx), %rcx
	movq	%rcx, -80(%rbp)
	movzbl	(%rdx), %edx
	movb	%dl, (%rax)
.L6:
	leaq	-64(%rbp), %rax
	cmpq	%rax, -80(%rbp)
	ja	.L7
	movq	-72(%rbp), %rax
	leaq	1(%rax), %rdx
	movq	%rdx, -72(%rbp)
	movq	-80(%rbp), %rdx
	movzbl	(%rdx), %edx
	movb	%dl, (%rax)
	movq	-72(%rbp), %rax
	leaq	1(%rax), %rdx
	movq	%rdx, -72(%rbp)
	movb	$10, (%rax)
	movq	-72(%rbp), %rax
	leaq	1(%rax), %rdx
	movq	%rdx, -72(%rbp)
	movb	$0, (%rax)
	jmp	.L8
.L3:
	leaq	-32(%rbp), %rax
	addq	$3, %rax
	movq	%rax, -72(%rbp)
.L8:
	movq	-72(%rbp), %rdx
	leaq	-32(%rbp), %rax
	subq	%rax, %rdx
	movq	%rdx, %rax
	leaq	-1(%rax), %rdx
	leaq	-32(%rbp), %rax
	movq	%rax, %rsi
	movl	$1, %edi
	movl	$0, %eax
	call	write
	movq	-8(%rbp), %rax
	xorq	%fs:40, %rax
	je	.L9
	call	__stack_chk_fail
.L9:
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	writeint, .-writeint
	.globl	readint
	.type	readint, @function
readint:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$64, %rsp
	movq	%fs:40, %rax
	movq	%rax, -8(%rbp)
	xorl	%eax, %eax
	movl	$20, -44(%rbp)
	movl	$0, -40(%rbp)
	movl	-44(%rbp), %edx
	leaq	-32(%rbp), %rax
	movq	%rax, %rsi
	movl	$0, %edi
	movl	$0, %eax
	call	read
	movl	%eax, -40(%rbp)
	movl	$0, -56(%rbp)
	movl	$1, -52(%rbp)
	movl	-40(%rbp), %eax
	movl	%eax, -48(%rbp)
	jmp	.L11
.L12:
	movl	-48(%rbp), %eax
	cltq
	movzbl	-32(%rbp,%rax), %eax
	movsbl	%al, %eax
	movl	%eax, -36(%rbp)
	movl	-36(%rbp), %eax
	imull	-52(%rbp), %eax
	addl	%eax, -56(%rbp)
	subl	$1, -48(%rbp)
	movl	-52(%rbp), %edx
	movl	%edx, %eax
	sall	$2, %eax
	addl	%edx, %eax
	addl	%eax, %eax
	movl	%eax, -52(%rbp)
.L11:
	cmpl	$0, -48(%rbp)
	jns	.L12
	movl	-56(%rbp), %eax
	movq	-8(%rbp), %rcx
	xorq	%fs:40, %rcx
	je	.L14
	call	__stack_chk_fail
.L14:
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	readint, .-readint
	.globl	multiply
	.type	multiply, @function
multiply:
.LFB2:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -20(%rbp)
	movl	%esi, -24(%rbp)
	movl	-20(%rbp), %eax
	imull	-24(%rbp), %eax
	movl	%eax, -4(%rbp)
	movl	-4(%rbp), %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	multiply, .-multiply
	.globl	divide
	.type	divide, @function
divide:
.LFB3:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -20(%rbp)
	movl	%esi, -24(%rbp)
	movl	-20(%rbp), %eax
	cltd
	idivl	-24(%rbp)
	movl	%eax, -4(%rbp)
	movl	-4(%rbp), %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE3:
	.size	divide, .-divide
	.globl	main
	.type	main, @function
main:
.LFB4:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movl	$0, %eax
	call	readint
	movl	%eax, -4(%rbp)
	movl	-4(%rbp), %eax
	movl	%eax, %edi
	call	writeint
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE4:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 4.9.2-10ubuntu13) 4.9.2"
	.section	.note.GNU-stack,"",@progbits

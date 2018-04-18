############################
# Line 6: d:int
############################
############################
# Line 8: meth2(a:int;b:int):intis
# c:int
# begin
# meth2:=a+b
# a:=10
# out.writeint(a)
# out.writeint(b)
# out.writeint(c)
# 
# endmeth2
############################
.text
.global main
.global _class_CBasics_method_meth2
.type _class_CBasics_method_meth2, @function
_class_CBasics_method_meth2:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 9: c:int
############################
movl $0, -8(%ebp)
############################
# Line 11: meth2:=a+b
############################
pushl 12(%ebp)
pushl 16(%ebp)
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, -4(%ebp)
############################
# Line 12: a:=10
############################
pushl $10
popl %eax
movl %eax, 12(%ebp)
############################
# Line 13: out.writeint(a)
############################
pushl 12(%ebp)
call writeint
addl $4, %esp
############################
# Line 14: out.writeint(b)
############################
pushl 16(%ebp)
call writeint
addl $4, %esp
############################
# Line 15: out.writeint(c)
############################
pushl -8(%ebp)
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 19: meth1(a:int;b:int):intis
# c:int
# begin
# d:=-5
# meth1:=meth2(a+b,d)
# out.writeint(a)
# out.writeint(b)
# endmeth1
############################
.global _class_CBasics_method_meth1
.type _class_CBasics_method_meth1, @function
_class_CBasics_method_meth1:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 20: c:int
############################
movl $0, -8(%ebp)
############################
# Line 22: d:=-5
############################
pushl $-5
popl %eax
movl 8(%ebp), %ebx
movl %eax, 8(%ebx)
############################
# Line 23: meth1:=meth2(a+b,d)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
pushl 12(%ebp)
pushl 16(%ebp)
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
pushl 8(%ebp)
call _class_CBasics_method_meth2
addl $12, %esp
pushl %eax
movl %eax, -4(%ebp)
############################
# Line 24: out.writeint(a)
############################
pushl 12(%ebp)
call writeint
addl $4, %esp
############################
# Line 25: out.writeint(b)
############################
pushl 16(%ebp)
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 28: start()is
# num:int
# x:int
# y:int
# begin
# x:=10
# y:=20
# out.writeint(num)
# out.writeint(x)
# out.writeint(y)
# out.writeint(d)
# num:=meth1(x,y)
# out.writeint(d)
# out.writeint(num)
# 
# endstart
############################
.global _class_CBasics_method_start
.type _class_CBasics_method_start, @function
_class_CBasics_method_start:
pushl %ebp
movl %esp, %ebp
subl $16, %esp
############################
# Line 29: num:int
############################
movl $0, -8(%ebp)
############################
# Line 30: x:int
############################
movl $0, -12(%ebp)
############################
# Line 31: y:int
############################
movl $0, -16(%ebp)
############################
# Line 33: x:=10
############################
pushl $10
popl %eax
movl %eax, -12(%ebp)
############################
# Line 34: y:=20
############################
pushl $20
popl %eax
movl %eax, -16(%ebp)
############################
# Line 35: out.writeint(num)
############################
pushl -8(%ebp)
call writeint
addl $4, %esp
############################
# Line 36: out.writeint(x)
############################
pushl -12(%ebp)
call writeint
addl $4, %esp
############################
# Line 37: out.writeint(y)
############################
pushl -16(%ebp)
call writeint
addl $4, %esp
############################
# Line 38: out.writeint(d)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
call writeint
addl $4, %esp
############################
# Line 39: num:=meth1(x,y)
############################
pushl -16(%ebp)
pushl -12(%ebp)
pushl 8(%ebp)
call _class_CBasics_method_meth1
addl $12, %esp
pushl %eax
movl %eax, -8(%ebp)
############################
# Line 40: out.writeint(d)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
call writeint
addl $4, %esp
############################
# Line 41: out.writeint(num)
############################
pushl -8(%ebp)
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
main:
pushl $12
pushl $1
call calloc
addl $8, %esp
pushl %eax
call _class_CBasics_method_start
popl %eax
ret

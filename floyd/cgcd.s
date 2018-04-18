############################
# Line 6: x:int
############################
############################
# Line 7: y:int
############################
############################
# Line 8: ans:int
############################
############################
# Line 10: gcd(a:int;b:int):intis
# begin
# ifb=0then
# gcd:=a
# else
# a:=gcd(b,a-(a/b)*b)
# gcd:=a
# endif
# endgcd
############################
.text
.global main
.global _class_Cgcd_method_gcd
.type _class_Cgcd_method_gcd, @function
_class_Cgcd_method_gcd:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 12: ifb=0then
# gcd:=a
# else
# a:=gcd(b,a-(a/b)*b)
# gcd:=a
# endif
############################
pushl 16(%ebp)
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
je _eqLine12
jmp _notEqLine12
_eqLine12:
pushl $1
jmp _endEqLine12
_notEqLine12:
pushl $0
_endEqLine12:
popl %eax
cmpl $0, %eax
jne _doifLine12
jmp _elseLine12
_doifLine12:
############################
# Line 13: gcd:=a
############################
pushl 12(%ebp)
popl %eax
movl %eax, -4(%ebp)
jmp _endIfLine12
_elseLine12:
############################
# Line 15: a:=gcd(b,a-(a/b)*b)
############################
pushl 12(%ebp)
pushl 16(%ebp)
pushl 16(%ebp)
pushl 12(%ebp)
call divide
popl %ecx
popl %ecx
pushl %eax
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
pushl 16(%ebp)
pushl 8(%ebp)
call _class_Cgcd_method_gcd
addl $12, %esp
pushl %eax
movl %eax, 12(%ebp)
############################
# Line 16: gcd:=a
############################
pushl 12(%ebp)
popl %eax
movl %eax, -4(%ebp)
_endIfLine12:
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 20: displayres(ans:int)is
# begin
# out.writeint(ans)
# enddisplayres
############################
.global _class_Cgcd_method_displayres
.type _class_Cgcd_method_displayres, @function
_class_Cgcd_method_displayres:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 22: out.writeint(ans)
############################
pushl 12(%ebp)
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 25: start()is
# begin
# x:=in.readint()
# y:=in.readint()
# ans:=gcd(x,y)
# displayres(ans)
# endstart
############################
.global _class_Cgcd_method_start
.type _class_Cgcd_method_start, @function
_class_Cgcd_method_start:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 27: x:=in.readint()
############################
call readint
pushl %eax
popl %eax
movl 8(%ebp), %ebx
movl %eax, 8(%ebx)
############################
# Line 28: y:=in.readint()
############################
call readint
pushl %eax
popl %eax
movl 8(%ebp), %ebx
movl %eax, 12(%ebx)
############################
# Line 29: ans:=gcd(x,y)
############################
movl 8(%ebp), %ebx
movl 12(%ebx), %eax
pushl %eax
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
pushl 8(%ebp)
call _class_Cgcd_method_gcd
addl $12, %esp
pushl %eax
movl 8(%ebp), %ebx
movl %eax, 16(%ebx)
############################
# Line 30: displayres(ans)
############################
movl 8(%ebp), %ebx
movl 16(%ebx), %eax
pushl %eax
pushl 8(%ebp)
call _class_Cgcd_method_displayres
addl $8, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
main:
pushl $20
pushl $1
call calloc
addl $8, %esp
pushl %eax
call _class_Cgcd_method_start
popl %eax
ret

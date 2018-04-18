############################
# Line 6: num:int
############################
############################
# Line 7: num2:int
############################
############################
# Line 9: Fact(num:int):intis
# answer:int
# begin
# ifnum=0then
# answer:=1
# else
# answer:=num*Fact(num-1)
# endif
# Fact:=answer
# endFact
############################
.text
.global main
.global _class_CFact_method_Fact
.type _class_CFact_method_Fact, @function
_class_CFact_method_Fact:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 10: answer:int
############################
movl $0, -8(%ebp)
############################
# Line 12: ifnum=0then
# answer:=1
# else
# answer:=num*Fact(num-1)
# endif
############################
pushl 12(%ebp)
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
# Line 13: answer:=1
############################
pushl $1
popl %eax
movl %eax, -8(%ebp)
jmp _endIfLine12
_elseLine12:
############################
# Line 15: answer:=num*Fact(num-1)
############################
pushl 12(%ebp)
pushl $1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
pushl 8(%ebp)
call _class_CFact_method_Fact
addl $8, %esp
pushl %eax
pushl 12(%ebp)
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, -8(%ebp)
_endIfLine12:
############################
# Line 17: Fact:=answer
############################
pushl -8(%ebp)
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 21: Go()is
# isOk:boolean
# begin
# isOk:=false
# loopwhilenotisOk
# num:=in.readint()
# isOk:=(num>=1)
# endloop
# endGo
############################
.global _class_CFact_method_Go
.type _class_CFact_method_Go, @function
_class_CFact_method_Go:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 22: isOk:boolean
############################
movl $0, -8(%ebp)
############################
# Line 24: isOk:=false
############################
pushl $0
popl %eax
movl %eax, -8(%ebp)
############################
# Line 25: loopwhilenotisOk
# num:=in.readint()
# isOk:=(num>=1)
# endloop
############################
_while25:
pushl -8(%ebp)
popl %eax
cmpl $0, %eax
je _eqLine25.0
jmp _notEqLine25.0
_eqLine25.0:
pushl $1
jmp _endEqLine25.0
_notEqLine25.0:
pushl $0
_endEqLine25.0:
popl %eax
cmpl $0, %eax
jne _startwhilebody25
jmp _endwhile25
_startwhilebody25:
############################
# Line 26: num:=in.readint()
############################
call readint
pushl %eax
popl %eax
movl 8(%ebp), %ebx
movl %eax, 8(%ebx)
############################
# Line 27: isOk:=(num>=1)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
pushl $1
popl %ebx
popl %eax
cmpl %ebx, %eax
jge _greaterOrEqLine27
jmp _notGreaterOrEqLine27
_greaterOrEqLine27:
pushl $1
jmp _endGreaterOrEqLine27
_notGreaterOrEqLine27:
pushl $0
_endGreaterOrEqLine27:
popl %eax
movl %eax, -8(%ebp)
jmp _while25
_endwhile25:
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 31: start()is
# begin
# Go()
# num2:=Fact(num)
# out.writeint(num)
# out.writeint(num2)
# endstart
############################
.global _class_CFact_method_start
.type _class_CFact_method_start, @function
_class_CFact_method_start:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 33: Go()
############################
pushl 8(%ebp)
call _class_CFact_method_Go
addl $8, %esp
############################
# Line 34: num2:=Fact(num)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
pushl 8(%ebp)
call _class_CFact_method_Fact
addl $8, %esp
pushl %eax
movl 8(%ebp), %ebx
movl %eax, 12(%ebx)
############################
# Line 35: out.writeint(num)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
call writeint
addl $4, %esp
############################
# Line 36: out.writeint(num2)
############################
movl 8(%ebp), %ebx
movl 12(%ebx), %eax
pushl %eax
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
main:
pushl $16
pushl $1
call calloc
addl $8, %esp
pushl %eax
call _class_CFact_method_start
popl %eax
ret

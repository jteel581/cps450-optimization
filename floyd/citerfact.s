.data
.comm _num, 4, 4
.comm _num2, 4, 4
.comm _isOk, 4, 4
############################
# Line 5: num:int
############################
############################
# Line 6: num2:int
############################
############################
# Line 7: isOk:boolean
############################
############################
# Line 9: Fact(num:int):intis
# answer:int
# begin
# answer:=1
# loopwhilenum>0
# answer:=answer*num
# num:=num-1
# endloop
# Fact:=answer
# out.writeint(0+answer-answer)
# endFact
############################
.text
.global main
.global Fact
.type Fact, @function
Fact:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 10: answer:int
############################
movl $0, -8(%ebp)
############################
# Line 12: answer:=1
############################
pushl $1
popl %eax
movl %eax, -8(%ebp)
############################
# Line 13: loopwhilenum>0
# answer:=answer*num
# num:=num-1
# endloop
############################
_while13:
pushl 8(%ebp)
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine13
jmp _notGreaterLine13
_greaterLine13:
pushl $1
jmp _endGreaterLine13
_notGreaterLine13:
pushl $0
_endGreaterLine13:
popl %eax
cmpl $0, %eax
jne _startwhilebody13
jmp _endwhile13
_startwhilebody13:
############################
# Line 14: answer:=answer*num
############################
pushl 8(%ebp)
pushl -8(%ebp)
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, -8(%ebp)
############################
# Line 15: num:=num-1
############################
pushl 8(%ebp)
pushl $1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, 8(%ebp)
jmp _while13
_endwhile13:
############################
# Line 17: Fact:=answer
############################
pushl -8(%ebp)
popl %eax
movl %eax, -4(%ebp)
############################
# Line 18: out.writeint(0+answer-answer)
############################
pushl $0
pushl -8(%ebp)
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
pushl -8(%ebp)
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 21: start()is
# begin
# num:=in.readint()
# ifnum>0then
# num:=Fact(num)
# out.writeint(num)
# endif
# endstart
############################
.global start
.type start, @function
start:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 23: num:=in.readint()
############################
call readint
pushl %eax
popl %eax
movl %eax, _num
############################
# Line 24: ifnum>0then
# num:=Fact(num)
# out.writeint(num)
# endif
############################
pushl _num
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine24
jmp _notGreaterLine24
_greaterLine24:
pushl $1
jmp _endGreaterLine24
_notGreaterLine24:
pushl $0
_endGreaterLine24:
popl %eax
cmpl $0, %eax
jne _doifLine24
jmp _elseLine24
_doifLine24:
############################
# Line 25: num:=Fact(num)
############################
pushl _num
call Fact
addl $4, %esp
pushl %eax
movl %eax, _num
############################
# Line 26: out.writeint(num)
############################
pushl _num
call writeint
addl $4, %esp
jmp _endIfLine24
_elseLine24:
_endIfLine24:
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
main:
call start
ret

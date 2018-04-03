.data
.comm _mov, 4, 4
.comm _x, 4, 4
.comm _y, 4, 4
############################
# Line 2: mov:int
############################
############################
# Line 3: x:int
############################
############################
# Line 4: y:int
############################
############################
# Line 9: mov:=12*in.readint()
############################
.text
.global main
main:
call readint
pushl %eax
pushl $12
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, _mov
############################
# Line 10: out.writeint(mov)
############################
pushl _mov
call writeint
addl $4, %esp
############################
# Line 12: mov:=mov/-1
############################
pushl $-1
pushl _mov
call divide
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, _mov
############################
# Line 13: out.writeint(mov)
############################
pushl _mov
call writeint
addl $4, %esp
############################
# Line 15: mov:=mov*-1
############################
pushl $-1
pushl _mov
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, _mov
############################
# Line 16: out.writeint(mov)
############################
pushl _mov
call writeint
addl $4, %esp
############################
# Line 18: if(true)then
# x:=1
# loopwhilenot(x>3)
# y:=2
# loopwhiley>-2
# y:=y-1
# ifnot(y>=0)then
# out.writeint(y)
# endif
# endloop
# out.writeint(x)
# x:=x+1
# endloop
# endif
############################
pushl $1
popl %eax
cmpl $0, %eax
jne _doifLine18
jmp _elseLine18
_doifLine18:
############################
# Line 19: x:=1
############################
pushl $1
popl %eax
movl %eax, _x
############################
# Line 20: loopwhilenot(x>3)
# y:=2
# loopwhiley>-2
# y:=y-1
# ifnot(y>=0)then
# out.writeint(y)
# endif
# endloop
# out.writeint(x)
# x:=x+1
# endloop
############################
_while20:
pushl _x
pushl $3
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine20
jmp _notGreaterLine20
_greaterLine20:
pushl $1
jmp _endGreaterLine20
_notGreaterLine20:
pushl $0
_endGreaterLine20:
popl %eax
cmpl $0, %eax
je _eqLine20.0
jmp _notEqLine20.0
_eqLine20.0:
pushl $1
jmp _endEqLine20.0
_notEqLine20.0:
pushl $0
_endEqLine20.0:
popl %eax
cmpl $0, %eax
jne _startwhilebody20
jmp _endwhile20
_startwhilebody20:
############################
# Line 21: y:=2
############################
pushl $2
popl %eax
movl %eax, _y
############################
# Line 22: loopwhiley>-2
# y:=y-1
# ifnot(y>=0)then
# out.writeint(y)
# endif
# endloop
############################
_while22:
pushl _y
pushl $-2
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine22
jmp _notGreaterLine22
_greaterLine22:
pushl $1
jmp _endGreaterLine22
_notGreaterLine22:
pushl $0
_endGreaterLine22:
popl %eax
cmpl $0, %eax
jne _startwhilebody22
jmp _endwhile22
_startwhilebody22:
############################
# Line 23: y:=y-1
############################
pushl _y
pushl $1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _y
############################
# Line 24: ifnot(y>=0)then
# out.writeint(y)
# endif
############################
pushl _y
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jge _greaterOrEqLine24
jmp _notGreaterOrEqLine24
_greaterOrEqLine24:
pushl $1
jmp _endGreaterOrEqLine24
_notGreaterOrEqLine24:
pushl $0
_endGreaterOrEqLine24:
popl %eax
cmpl $0, %eax
je _eqLine24.1
jmp _notEqLine24.1
_eqLine24.1:
pushl $1
jmp _endEqLine24.1
_notEqLine24.1:
pushl $0
_endEqLine24.1:
popl %eax
cmpl $0, %eax
jne _doifLine24
jmp _elseLine24
_doifLine24:
############################
# Line 25: out.writeint(y)
############################
pushl _y
call writeint
addl $4, %esp
jmp _endIfLine24
_elseLine24:
_endIfLine24:
jmp _while22
_endwhile22:
############################
# Line 28: out.writeint(x)
############################
pushl _x
call writeint
addl $4, %esp
############################
# Line 29: x:=x+1
############################
pushl _x
pushl $1
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _x
jmp _while20
_endwhile20:
jmp _endIfLine18
_elseLine18:
_endIfLine18:
ret

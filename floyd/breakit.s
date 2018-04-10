.data
.comm _mov, 4, 4
.comm _x, 4, 4
.comm _y, 4, 4
.comm _j, 4, 4
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
# Line 6: start()is
# j:int
# begin
# j:=1
# mov:=12*in.readint()
# out.writeint(mov)
# 
# mov:=mov/-1
# out.writeint(mov)
# 
# mov:=mov*-1
# out.writeint(mov)
# 
# if(true)then
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
# endstart
############################
.text
.global main
main:
.global start
.type start, @function
start:
pushl %ebp
movl %esp, %ebp
############################
# Line 7: j:int
############################
############################
# Line 9: j:=1
############################
pushl $1
popl %eax
movl %eax, -8(%ebp)
############################
# Line 10: mov:=12*in.readint()
############################
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
# Line 11: out.writeint(mov)
############################
pushl _mov
call writeint
addl $4, %esp
############################
# Line 13: mov:=mov/-1
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
# Line 14: out.writeint(mov)
############################
pushl _mov
call writeint
addl $4, %esp
############################
# Line 16: mov:=mov*-1
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
# Line 17: out.writeint(mov)
############################
pushl _mov
call writeint
addl $4, %esp
############################
# Line 19: if(true)then
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
jne _doifLine19
jmp _elseLine19
_doifLine19:
############################
# Line 20: x:=1
############################
pushl $1
popl %eax
movl %eax, _x
############################
# Line 21: loopwhilenot(x>3)
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
_while21:
pushl _x
pushl $3
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine21
jmp _notGreaterLine21
_greaterLine21:
pushl $1
jmp _endGreaterLine21
_notGreaterLine21:
pushl $0
_endGreaterLine21:
popl %eax
cmpl $0, %eax
je _eqLine21.0
jmp _notEqLine21.0
_eqLine21.0:
pushl $1
jmp _endEqLine21.0
_notEqLine21.0:
pushl $0
_endEqLine21.0:
popl %eax
cmpl $0, %eax
jne _startwhilebody21
jmp _endwhile21
_startwhilebody21:
############################
# Line 22: y:=2
############################
pushl $2
popl %eax
movl %eax, _y
############################
# Line 23: loopwhiley>-2
# y:=y-1
# ifnot(y>=0)then
# out.writeint(y)
# endif
# endloop
############################
_while23:
pushl _y
pushl $-2
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine23
jmp _notGreaterLine23
_greaterLine23:
pushl $1
jmp _endGreaterLine23
_notGreaterLine23:
pushl $0
_endGreaterLine23:
popl %eax
cmpl $0, %eax
jne _startwhilebody23
jmp _endwhile23
_startwhilebody23:
############################
# Line 24: y:=y-1
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
# Line 25: ifnot(y>=0)then
# out.writeint(y)
# endif
############################
pushl _y
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jge _greaterOrEqLine25
jmp _notGreaterOrEqLine25
_greaterOrEqLine25:
pushl $1
jmp _endGreaterOrEqLine25
_notGreaterOrEqLine25:
pushl $0
_endGreaterOrEqLine25:
popl %eax
cmpl $0, %eax
je _eqLine25.1
jmp _notEqLine25.1
_eqLine25.1:
pushl $1
jmp _endEqLine25.1
_notEqLine25.1:
pushl $0
_endEqLine25.1:
popl %eax
cmpl $0, %eax
jne _doifLine25
jmp _elseLine25
_doifLine25:
############################
# Line 26: out.writeint(y)
############################
pushl _y
call writeint
addl $4, %esp
jmp _endIfLine25
_elseLine25:
_endIfLine25:
jmp _while23
_endwhile23:
############################
# Line 29: out.writeint(x)
############################
pushl _x
call writeint
addl $4, %esp
############################
# Line 30: x:=x+1
############################
pushl _x
pushl $1
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _x
jmp _while21
_endwhile21:
jmp _endIfLine19
_elseLine19:
_endIfLine19:
popl %ebp
ret 
ret

############################
# Line 2: x:int
############################
.data
.comm _x, 4, 4
############################
# Line 3: y:boolean
############################
.comm _y, 4, 4
############################
# Line 4: z:boolean
############################
.comm _z, 4, 4
############################
# Line 6: ab:int
############################
.comm _ab, 4, 4
############################
# Line 8: ab:=20
############################
.text
.global main
main:
pushl $20
popl %eax
movl %eax, _ab
############################
# Line 9: ifab=20then
# ab:=22
# ifab=22then
# ab:=25
# endif
# else
# ab:=27
# endif
############################
pushl _ab
pushl $20
popl %ebx
popl %eax
cmpl %eax, %ebx
je _eqLine9
jmp _notEqLine9
_eqLine9:
pushl $1
jmp _endEqLine9
_notEqLine9:
pushl $0
_endEqLine9:
popl %eax
cmpl $0, %eax
jne _doifLine9
jmp _elseLine9
_doifLine9:
############################
# Line 10: ab:=22
############################
pushl $22
popl %eax
movl %eax, _ab
############################
# Line 11: ifab=22then
# ab:=25
# endif
############################
pushl _ab
pushl $22
popl %ebx
popl %eax
cmpl %eax, %ebx
je _eqLine11
jmp _notEqLine11
_eqLine11:
pushl $1
jmp _endEqLine11
_notEqLine11:
pushl $0
_endEqLine11:
popl %eax
cmpl $0, %eax
jne _doifLine11
jmp _elseLine11
_doifLine11:
############################
# Line 12: ab:=25
############################
pushl $25
popl %eax
movl %eax, _ab
jmp _endGreaterLine11
_elseLine11:
_endGreaterLine11:
jmp _endGreaterLine9
_elseLine9:
############################
# Line 15: ab:=27
############################
pushl $27
popl %eax
movl %eax, _ab
_endGreaterLine9:
############################
# Line 17: loopwhileab>20
# ab:=ab-1
# endloop
############################
_while17:
pushl _ab
pushl $20
popl %ebx
popl %eax
cmpl %eax, %ebx
jg _greaterLine17
jmp _notGreaterLine17
_greaterLine17:
pushl $1
jmp _endGreaterLine17
_notGreaterLine17:
pushl $0
_endGreaterLine17:
popl %eax
cmpl $0, %eax
jne _startwhilebody17
jmp _endwhile17
_startwhilebody17:
############################
# Line 18: ab:=ab-1
############################
pushl _ab
pushl $1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _ab
jmp _while17
_endwhile17:
############################
# Line 20: x:=1+-ab
############################
pushl $1
pushl _ab
popl %eax
negl %eax
pushl %eax
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _x
############################
# Line 21: y:=trueorfalse
############################
pushl $1
pushl $0
popl %ebx
popl %eax
orl %eax, %ebx
pushl %eax
popl %eax
movl %eax, _y
############################
# Line 22: z:=y
############################
pushl _y
popl %eax
movl %eax, _z
############################
# Line 23: x:=in.readint()
############################
call readint
pushl %eax
popl %eax
movl %eax, _x
############################
# Line 24: in.readint()
############################
call readint
############################
# Line 25: out.writeint(ab)
############################
pushl _ab
call writeint
popl %ecx
ret

############################
# Line 6: b1:boolean
############################
.data
.comm _b1, 4, 4
############################
# Line 7: b2:boolean
############################
.comm _b2, 4, 4
############################
# Line 9: b3:boolean
############################
.comm _b3, 4, 4
############################
# Line 11: i1:int
############################
.comm _i1, 4, 4
############################
# Line 12: i2:int
############################
.comm _i2, 4, 4
############################
# Line 17: b1:=true
############################
.text
.global main
main:
pushl $1
popl %eax
movl %eax, _b1
############################
# Line 18: b2:=notb1andtrue
############################
pushl _b1
popl %eax
notl %eax
pushl %eax
pushl $1
popl %ebx
popl %eax
andl %eax, %ebx
pushl %eax
popl %eax
movl %eax, _b2
############################
# Line 19: b3:=i1>i2
############################
pushl _i1
pushl _i2
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine19
jmp _notGreaterLine19
_greaterLine19:
pushl $1
jmp _endGreaterLine19
_notGreaterLine19:
pushl $0
_endGreaterLine19:
popl %eax
movl %eax, _b3
############################
# Line 20: b2:=false
############################
pushl $0
popl %eax
movl %eax, _b2
############################
# Line 21: b3:=b1ortrue
############################
pushl _b1
pushl $1
popl %ebx
popl %eax
orl %eax, %ebx
pushl %eax
popl %eax
movl %eax, _b3
############################
# Line 23: i1:=10
############################
pushl $10
popl %eax
movl %eax, _i1
############################
# Line 24: i1:=10*i2-i1
############################
pushl _i2
pushl $10
call multiply
popl %ecx
popl %ecx
pushl %eax
pushl _i1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _i1
############################
# Line 25: i1:=0+(3-4)*-1
############################
pushl $0
pushl $-1
pushl $3
pushl $4
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _i1
############################
# Line 26: i1:=10+i2-i1
############################
pushl $10
pushl _i2
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
pushl _i1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _i1
ret

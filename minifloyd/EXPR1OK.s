############################
# Line 6: n1:int
############################
.data
.comm _n1, 4, 4
############################
# Line 7: n2:int
############################
.comm _n2, 4, 4
############################
# Line 8: b1:boolean
############################
.comm _b1, 4, 4
############################
# Line 9: b2:boolean
############################
.comm _b2, 4, 4
############################
# Line 15: b1:=b1
############################
.text
.global main
main:
pushl _b1
popl %eax
movl %eax, _b1
############################
# Line 16: b1:=b2
############################
pushl _b2
popl %eax
movl %eax, _b1
############################
# Line 17: b2:=trueand(notb1)
############################
pushl $1
pushl _b1
popl %eax
notl %eax
pushl %eax
popl %ebx
popl %eax
andl %eax, %ebx
pushl %eax
popl %eax
movl %eax, _b2
############################
# Line 18: n1:=5*15-2
############################
pushl $15
pushl $5
call multiply
popl %ecx
popl %ecx
pushl %eax
pushl $2
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _n1
############################
# Line 19: n2:=-35+15*n1/n2
############################
pushl $-35
pushl _n2
pushl _n1
pushl $15
call multiply
popl %ecx
popl %ecx
pushl %eax
call divide
popl %ecx
popl %ecx
pushl %eax
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _n2
############################
# Line 22: b1:=b2
############################
pushl _b2
popl %eax
movl %eax, _b1
############################
# Line 23: n2:=5*3
############################
pushl $3
pushl $5
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, _n2
############################
# Line 24: b2:=true
############################
pushl $1
popl %eax
movl %eax, _b2
############################
# Line 26: b2:=false
############################
pushl $0
popl %eax
movl %eax, _b2
############################
# Line 27: b2:=notb1
############################
pushl _b1
popl %eax
notl %eax
pushl %eax
popl %eax
movl %eax, _b2
ret

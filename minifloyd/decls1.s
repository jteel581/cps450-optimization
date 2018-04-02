.data
.comm _x, 4, 4
.comm _y, 4, 4
.comm _z, 4, 4
############################
# Line 8: x:int
############################
############################
# Line 9: y:boolean
############################
############################
# Line 10: z:int
############################
############################
# Line 14: x:=3
############################
.text
.global main
main:
pushl $3
popl %eax
movl %eax, _x
############################
# Line 15: z:=x-1
############################
pushl _x
pushl $1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, _z
############################
# Line 17: y:=true
############################
pushl $1
popl %eax
movl %eax, _y
ret

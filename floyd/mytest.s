############################
# Line 2: add(x:int;y:int):intis
# result:int
# begin
# result:=x+y
# add:=result
# endadd
############################
.text
.global main
.global add
.type add, @function
add:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 3: result:int
############################
movl $0, -8(%ebp)
############################
# Line 5: result:=x+y
############################
pushl 8(%ebp)
pushl 12(%ebp)
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
popl %eax
movl %eax, -8(%ebp)
############################
# Line 6: add:=result
############################
pushl -8(%ebp)
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
popl %ebp
ret 
############################
# Line 8: oneThat(a:int):intis
# begin
# ifa=1then
# oneThat:=a
# else
# oneThat:=oneThat(a-1)
# endif
# endoneThat
############################
.global oneThat
.type oneThat, @function
oneThat:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 10: ifa=1then
# oneThat:=a
# else
# oneThat:=oneThat(a-1)
# endif
############################
pushl 8(%ebp)
pushl $1
popl %ebx
popl %eax
cmpl %ebx, %eax
je _eqLine10
jmp _notEqLine10
_eqLine10:
pushl $1
jmp _endEqLine10
_notEqLine10:
pushl $0
_endEqLine10:
popl %eax
cmpl $0, %eax
jne _doifLine10
jmp _elseLine10
_doifLine10:
############################
# Line 11: oneThat:=a
############################
pushl 8(%ebp)
popl %eax
movl %eax, -4(%ebp)
jmp _endIfLine10
_elseLine10:
############################
# Line 13: oneThat:=oneThat(a-1)
############################
pushl 8(%ebp)
pushl $1
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
call oneThat
addl $4, %esp
movl %eax, -4(%ebp)
_endIfLine10:
movl %ebp, %esp
popl %ebp
ret 
############################
# Line 16: start()is
# result:int
# begin
# result:=oneThat(10)
# writeint(result)
# endstart
############################
.global start
.type start, @function
start:
pushl %ebp
movl %esp, %ebp
subl $8, %esp
############################
# Line 17: result:int
############################
movl $0, -8(%ebp)
############################
# Line 19: result:=oneThat(10)
############################
pushl $10
call oneThat
addl $4, %esp
movl %eax, -8(%ebp)
############################
# Line 20: writeint(result)
############################
pushl -8(%ebp)
call writeint
addl $4, %esp
movl %ebp, %esp
popl %ebp
ret 
main:
call start
ret

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
# Line 8: start()is
# result:int
# begin
# result:=add(1,2)
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
# Line 9: result:int
############################
movl $0, -8(%ebp)
############################
# Line 11: result:=add(1,2)
############################
pushl $2
pushl $1
call add
addl $8, %esp
movl %eax, -8(%ebp)
############################
# Line 12: writeint(result)
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

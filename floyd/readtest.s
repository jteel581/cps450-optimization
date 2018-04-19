############################
# Line 2: start()is
# begin
# out.writeint(in.readint())
# out.writeint(in.readint())
# out.writeint(in.readint()+in.readint())
# endstart
############################
.text
.global main
.global _class_readtest_method_start
.type _class_readtest_method_start, @function
_class_readtest_method_start:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 4: out.writeint(in.readint())
############################
call readint
pushl %eax
call writeint
addl $4, %esp
############################
# Line 5: out.writeint(in.readint())
############################
call readint
pushl %eax
call writeint
addl $4, %esp
############################
# Line 6: out.writeint(in.readint()+in.readint())
############################
call readint
pushl %eax
call readint
pushl %eax
popl %ebx
popl %eax
addl %ebx, %eax
pushl %eax
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
main:
pushl $8
pushl $1
call calloc
addl $8, %esp
pushl %eax
call _class_readtest_method_start
popl %eax
ret

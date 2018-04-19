############################
# Line 5: x:int
############################
############################
# Line 6: y:int
############################
############################
# Line 8: init(initX:int;initY:int):Pointis
# begin
# x:=initX
# y:=initY
# init:=me
# endinit
############################
.text
.global main
.global _class_Point_method_init
.type _class_Point_method_init, @function
_class_Point_method_init:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 10: x:=initX
############################
pushl 12(%ebp)
popl %eax
movl 8(%ebp), %ebx
movl %eax, 8(%ebx)
############################
# Line 11: y:=initY
############################
pushl 16(%ebp)
popl %eax
movl 8(%ebp), %ebx
movl %eax, 12(%ebx)
############################
# Line 12: init:=me
############################
pushl 8(%ebp)
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 15: getX():intis
# begin
# getX:=x
# endgetX
############################
.global _class_Point_method_getX
.type _class_Point_method_getX, @function
_class_Point_method_getX:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 17: getX:=x
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 20: getY():intis
# begin
# getY:=y
# endgetY
############################
.global _class_Point_method_getY
.type _class_Point_method_getY, @function
_class_Point_method_getY:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 22: getY:=y
############################
movl 8(%ebp), %ebx
movl 12(%ebx), %eax
pushl %eax
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 25: print()is
# begin
# out.writeint(x)
# out.writeint(y)
# endprint
############################
.global _class_Point_method_print
.type _class_Point_method_print, @function
_class_Point_method_print:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 27: out.writeint(x)
############################
movl 8(%ebp), %ebx
movl 8(%ebx), %eax
pushl %eax
call writeint
addl $4, %esp
############################
# Line 28: out.writeint(y)
############################
movl 8(%ebp), %ebx
movl 12(%ebx), %eax
pushl %eax
call writeint
addl $4, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 31: setXY(newX:int;newY:int)is
# begin
# x:=newX
# y:=newY
# endsetXY
############################
.global _class_Point_method_setXY
.type _class_Point_method_setXY, @function
_class_Point_method_setXY:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 33: x:=newX
############################
pushl 12(%ebp)
popl %eax
movl 8(%ebp), %ebx
movl %eax, 8(%ebx)
############################
# Line 34: y:=newY
############################
pushl 16(%ebp)
popl %eax
movl 8(%ebp), %ebx
movl %eax, 12(%ebx)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 37: setX(newX:int)is
# begin
# setXY(newX,y)
# endsetX
############################
.global _class_Point_method_setX
.type _class_Point_method_setX, @function
_class_Point_method_setX:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 39: setXY(newX,y)
############################
movl 8(%ebp), %ebx
movl 12(%ebx), %eax
pushl %eax
pushl 12(%ebp)
pushl 8(%ebp)
call _class_Point_method_setXY
addl $12, %esp
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 42: setY(newY:int)is
# begin
# y:=newY
# endsetY
############################
.global _class_Point_method_setY
.type _class_Point_method_setY, @function
_class_Point_method_setY:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 44: y:=newY
############################
pushl 12(%ebp)
popl %eax
movl 8(%ebp), %ebx
movl %eax, 12(%ebx)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 52: start()is
# p1:Point
# p2:Point
# begin
# p1:=newPoint
# p1.init(5,10)
# 
# p2:=(newPoint).init(100,200)
# 
# p1.print()
# p2.print()
# 
# p1.setX(-5)
# p1.print()
# p2.print()
# 
# endstart
############################
.global _class_Bobjbasics_method_start
.type _class_Bobjbasics_method_start, @function
_class_Bobjbasics_method_start:
pushl %ebp
movl %esp, %ebp
subl $12, %esp
############################
# Line 53: p1:Point
############################
movl $0, -8(%ebp)
############################
# Line 54: p2:Point
############################
movl $0, -12(%ebp)
############################
# Line 56: p1:=newPoint
############################
pushl $16
pushl $1
call calloc
addl $8, %esp
pushl %eax
popl %eax
movl %eax, -8(%ebp)
############################
# Line 57: p1.init(5,10)
############################
pushl $10
pushl $5
pushl -8(%ebp)
call _class_Point_method_init
addl $8, %esp
############################
# Line 59: p2:=(newPoint).init(100,200)
############################
pushl $16
pushl $1
call calloc
addl $8, %esp
pushl %eax
popl %eax
pushl $200
pushl $100
pushl %eax
call _class_Point_method_init
addl $12, %esp
pushl %eax
popl %eax
movl %eax, -12(%ebp)
############################
# Line 61: p1.print()
############################
pushl -8(%ebp)
call _class_Point_method_print
addl $4, %esp
############################
# Line 62: p2.print()
############################
pushl -12(%ebp)
call _class_Point_method_print
addl $4, %esp
############################
# Line 64: p1.setX(-5)
############################
pushl $-5
pushl -8(%ebp)
call _class_Point_method_setX
addl $4, %esp
############################
# Line 65: p1.print()
############################
pushl -8(%ebp)
call _class_Point_method_print
addl $4, %esp
############################
# Line 66: p2.print()
############################
pushl -12(%ebp)
call _class_Point_method_print
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
call _class_Bobjbasics_method_start
popl %eax
ret

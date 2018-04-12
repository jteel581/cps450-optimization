.data
.comm _Amt, 4, 4
.comm _Quarters, 4, 4
.comm _Dimes, 4, 4
.comm _Nickels, 4, 4
############################
# Line 6: Amt:int
############################
############################
# Line 7: Quarters:int
############################
############################
# Line 8: Dimes:int
############################
############################
# Line 9: Nickels:int
############################
############################
# Line 11: ComputeChange(amt:int;denom:int):intis
# begin
# ComputeChange:=amt/denom
# endComputeChange
############################
.text
.global main
.global ComputeChange
.type ComputeChange, @function
ComputeChange:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 13: ComputeChange:=amt/denom
############################
pushl 12(%ebp)
pushl 8(%ebp)
call divide
popl %ecx
popl %ecx
pushl %eax
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 16: ComputeRemain(amt:int;denom:int;qtydenom:int):intis
# begin
# ComputeRemain:=amt-denom*qtydenom
# endComputeRemain
############################
.global ComputeRemain
.type ComputeRemain, @function
ComputeRemain:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 18: ComputeRemain:=amt-denom*qtydenom
############################
pushl 8(%ebp)
pushl 16(%ebp)
pushl 12(%ebp)
call multiply
popl %ecx
popl %ecx
pushl %eax
popl %ebx
popl %eax
subl %ebx, %eax
pushl %eax
popl %eax
movl %eax, -4(%ebp)
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
############################
# Line 21: start()is
# begin
# Amt:=in.readint()
# out.writeint(Amt)
# 
# 
# Quarters:=ComputeChange(Amt,25)
# if(Quarters>0)then
# out.writeint(Quarters)
# else
# out.writeint(0)
# endif
# Amt:=ComputeRemain(Amt,25,Quarters)
# 
# 
# Dimes:=ComputeChange(Amt,10)
# if(Dimes>0)then
# out.writeint(Dimes)
# else
# out.writeint(0)
# endif
# Amt:=ComputeRemain(Amt,10,Dimes)
# 
# Nickels:=ComputeChange(Amt,5)
# if(Nickels>0)then
# out.writeint(Nickels)
# else
# out.writeint(0)
# endif
# 
# Amt:=ComputeRemain(Amt,5,Nickels)
# 
# if(Amt>0)then
# out.writeint(Amt)
# endif
# endstart
############################
.global start
.type start, @function
start:
pushl %ebp
movl %esp, %ebp
subl $4, %esp
############################
# Line 23: Amt:=in.readint()
############################
call readint
pushl %eax
popl %eax
movl %eax, _Amt
############################
# Line 24: out.writeint(Amt)
############################
pushl _Amt
call writeint
addl $4, %esp
############################
# Line 27: Quarters:=ComputeChange(Amt,25)
############################
pushl $25
pushl _Amt
call ComputeChange
addl $8, %esp
movl %eax, _Quarters
############################
# Line 28: if(Quarters>0)then
# out.writeint(Quarters)
# else
# out.writeint(0)
# endif
############################
pushl _Quarters
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine28
jmp _notGreaterLine28
_greaterLine28:
pushl $1
jmp _endGreaterLine28
_notGreaterLine28:
pushl $0
_endGreaterLine28:
popl %eax
cmpl $0, %eax
jne _doifLine28
jmp _elseLine28
_doifLine28:
############################
# Line 29: out.writeint(Quarters)
############################
pushl _Quarters
call writeint
addl $4, %esp
jmp _endIfLine28
_elseLine28:
############################
# Line 31: out.writeint(0)
############################
pushl $0
call writeint
addl $4, %esp
_endIfLine28:
############################
# Line 33: Amt:=ComputeRemain(Amt,25,Quarters)
############################
pushl _Quarters
pushl $25
pushl _Amt
call ComputeRemain
addl $12, %esp
movl %eax, _Amt
############################
# Line 36: Dimes:=ComputeChange(Amt,10)
############################
pushl $10
pushl _Amt
call ComputeChange
addl $8, %esp
movl %eax, _Dimes
############################
# Line 37: if(Dimes>0)then
# out.writeint(Dimes)
# else
# out.writeint(0)
# endif
############################
pushl _Dimes
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine37
jmp _notGreaterLine37
_greaterLine37:
pushl $1
jmp _endGreaterLine37
_notGreaterLine37:
pushl $0
_endGreaterLine37:
popl %eax
cmpl $0, %eax
jne _doifLine37
jmp _elseLine37
_doifLine37:
############################
# Line 38: out.writeint(Dimes)
############################
pushl _Dimes
call writeint
addl $4, %esp
jmp _endIfLine37
_elseLine37:
############################
# Line 40: out.writeint(0)
############################
pushl $0
call writeint
addl $4, %esp
_endIfLine37:
############################
# Line 42: Amt:=ComputeRemain(Amt,10,Dimes)
############################
pushl _Dimes
pushl $10
pushl _Amt
call ComputeRemain
addl $12, %esp
movl %eax, _Amt
############################
# Line 44: Nickels:=ComputeChange(Amt,5)
############################
pushl $5
pushl _Amt
call ComputeChange
addl $8, %esp
movl %eax, _Nickels
############################
# Line 45: if(Nickels>0)then
# out.writeint(Nickels)
# else
# out.writeint(0)
# endif
############################
pushl _Nickels
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine45
jmp _notGreaterLine45
_greaterLine45:
pushl $1
jmp _endGreaterLine45
_notGreaterLine45:
pushl $0
_endGreaterLine45:
popl %eax
cmpl $0, %eax
jne _doifLine45
jmp _elseLine45
_doifLine45:
############################
# Line 46: out.writeint(Nickels)
############################
pushl _Nickels
call writeint
addl $4, %esp
jmp _endIfLine45
_elseLine45:
############################
# Line 48: out.writeint(0)
############################
pushl $0
call writeint
addl $4, %esp
_endIfLine45:
############################
# Line 51: Amt:=ComputeRemain(Amt,5,Nickels)
############################
pushl _Nickels
pushl $5
pushl _Amt
call ComputeRemain
addl $12, %esp
movl %eax, _Amt
############################
# Line 53: if(Amt>0)then
# out.writeint(Amt)
# endif
############################
pushl _Amt
pushl $0
popl %ebx
popl %eax
cmpl %ebx, %eax
jg _greaterLine53
jmp _notGreaterLine53
_greaterLine53:
pushl $1
jmp _endGreaterLine53
_notGreaterLine53:
pushl $0
_endGreaterLine53:
popl %eax
cmpl $0, %eax
jne _doifLine53
jmp _elseLine53
_doifLine53:
############################
# Line 54: out.writeint(Amt)
############################
pushl _Amt
call writeint
addl $4, %esp
jmp _endIfLine53
_elseLine53:
_endIfLine53:
movl %ebp, %esp
movl -4(%ebp), %eax
popl %ebp
ret 
main:
call start
ret

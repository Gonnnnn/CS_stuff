.data

info0:
  .asciiz "The string to be sorted is: "
info1:
  .asciiz "The sorted string is: "
newline:
  .asciiz "\n"

str_buf:
  .asciiz "this is a string."
str_len:
  .word 17

.text

################################################################################
# merge
#
# INPUTs
#   $a0: the starting memory address of a string (i.e., str)
#   $a1: the first index of the range of the string to be merged (i.e., l)
#   $a2: the middle index of the range of the string to be merged (i.e., m)
#   $a3: the last index of the range of the string to be merged (i.e., r)
#
merge:
  # stack.push($ra) <-- modify this part depending w.r.t. your implementation
  addi $sp, $sp, -8
  sw $ra, 0($sp)
  sw $s0, 4($sp)

while:
  # compare
  slt $t8, $a1, $a2
  slt $t9, $a2, $a3
  and $t0, $t8, $t9
  
  bne $t0, 1, endMerge

  # if True, then compare str[l] and str[m]
  # address : $s0 = str + l, $s1 = str + m
  add $s0, $a0, $a1
  add $s1, $a0, $a2

  # $t0 = str[l], $t1 = str[m]
  lb $t0, 0($s0)
  lb $t1, 0($s1)
  slt $t8, $t1, $t0
  bne $t8, 1, else

  # $t2 = tmp = str[m]
  move $t2, $t1
  # $t9 = i = m
  move $t9, $a2
  
loop:
  slt $t8, $a1, $t9
  bne $t8, 1, endLoop

  # $t3 = str + i - 1
  add $t3, $a0, $t9
  addi $t3, $t3, -1
  # $t4 = str[i-1]
  lb $t4, 0($t3)
  # str[l] = str[i-1]
  addi $t3, $t3, 1
  sb $t4, 0($t3)

  addi $t9, $t9, -1

  j loop

endLoop:
  # str[l] = tmp
  sb $t2, 0($s0)
  addi $a2, $a2, 1

else:
  addi $a1, $a1, 1
  j while

endMerge:
  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  lw $s0, 4($sp)
  addi $sp, $sp, 8

  jr $ra
################################################################################

################################################################################
# mergeSort
#
# INPUTs
#   $a0: the starting memory address of a string (i.e., str)
#   $a1: the first index of the range of the string to be sorted (i.e., l)
#   $a2: the last index of the range of the string to be sorted (i.e., r)
#
mergeSort:
  # stack.push($ra) <-- modify this part depending w.r.t. your implementation
  addi $sp, $sp, -8
  sw $ra, 0($sp)
  sw $s0, 4($sp)
  
  # $t1 = r-1
  addi $t1, $a2, -1
  slt $t0, $a1, $t1
  bne $t0, 1, endMergeSort

  # $s0 = m = (l + r + 1) / 2
  addu $s0, $a1, $a2
  addi $s0, $s0, 1
  addi $t0, $zero, 2
  divu $s0, $t0
  mflo $s0

  # load $a1, $a2 on stack
  addi $sp, $sp, -8
  sw $a1, 0($sp)
  sw $a2, 4($sp)

  # assign m to $a2. $a1 is already l
  move $a2, $s0
  jal mergeSort
  
  # assign m to $a1, load r to $a2
  move $a1, $s0
  lw $a2, 4($sp)
  jal mergeSort

  # load l to $a1, move m to $a2, load r to $a3
  lw $a1, 0($sp)
  move $a2, $s0
  lw $a3, 4($sp)
  jal merge

  lw $a1, 0($sp)
  lw $a2, 4($sp)
  addi $sp, $sp, 8

endMergeSort:
  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  lw $s0, 4($sp)
  addi $sp, $sp, 8

  jr $ra
################################################################################

.globl main
main:

  # stack.push($ra)
  addi $sp, $sp, -4
  sw $ra, 0($sp)

  # print_string(info0)
  li $v0, 4
  la $a0, info0
  syscall
  # print_string(str_buf)
  li $v0, 4
  la $a0, str_buf
  syscall
  # print_string(newline)
  li $v0, 4
  la $a0, newline
  syscall

  # mergeSort(str_buf, 0, str_len)
  la $a0, str_buf
  li $a1, 0
  lw $a2, str_len
  jal mergeSort

  # print_string(info1)
  li $v0, 4
  la $a0, info1
  syscall
  # print_string(str_buf)
  li $v0, 4
  la $a0, str_buf
  syscall
  # print_string(newline)
  li $v0, 4
  la $a0, newline
  syscall

  # stack.pop($ra)
  lw $ra, 0($sp)
  addi $sp, $sp, 4

  # return
  jr $ra
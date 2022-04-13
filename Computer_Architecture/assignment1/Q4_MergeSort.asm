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
  addi $sp, $sp, -4
  sw $ra, 0($sp)

  # FIXME
  nop

  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  addi $sp, $sp, 4

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
  addi $sp, $sp, -4
  sw $ra, 0($sp)

  # FIXME
  nop

  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  addi $sp, $sp, 4

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

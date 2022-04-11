.data

str0:
  .asciiz "TestString0"
str1:
  .asciiz "TestString1"

log_str0:
  .asciiz "INFO: compareStrings returned: "
log_newline:
  .asciiz "\n"

.text

################################################################################
# compareStrings
#
# INPUTs
#   $a0: the starting memory address of str0
#   $a1: the starting memory address of str1
#
# OUTPUTs
#   $v0: a 32-bit signed integer indicating the comparison result
#
compareStrings:
  # stack.push($ra) <-- modify this part depending w.r.t. your implementation
  addi $sp, $sp, -4
  sw $ra, 0($sp)

  li $v0, -1
  move $s0, $a0
  move $t1, $s0

  li $t0, 0
  jal count

  move $a0, $t0

  #####
  li $v0, 1
  syscall
  #####

  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  addi $sp, $sp, 4
 
  jr $ra
################################################################################

count:
  # $t0 : length
  # $t1 : address
  # $t2 : value
  lb $t2, 0($t1)
  beq $t2, 0, endCount
  
  addi $t0, $t0, 1
  addi $t1, $t1, 1

  j count

endCount:
  jr $ra

.globl main
main:

  # stack.push($ra)
  addi $sp, $sp, -4
  sw $ra, 0($sp)

  # $t0 = compareStrings(str0, str1)
  la $a0, str0
  la $a1, str1
  jal compareStrings
  move $t0, $v0

  # print(log_str0, $t0, log_newline)
  li $v0, 4
  la $a0, log_str0
  syscall
  li $v0, 1
  move $a0, $t0
  syscall
  li $v0, 4
  la $a0, log_newline
  syscall

  # $ra = stack.pop()
  lw $ra, 0($sp)
  addi $sp, $sp, 4

  # return
  jr $ra

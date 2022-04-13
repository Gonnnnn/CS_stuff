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

  # save the length of str0 and str1 on $s0, $s1
  li $t0, -1
  move $t1, $a0
  addi $t1, -1

count1:
  addi $t0, $t0, 1
  addi $t1, $t1, 1

  lb $t2, 0($t1)
  bne $t2, 0, count1
  move $s0, $t0

  li $t0, -1
  move $t1, $a1
  addi $t1, -1

count2:
  addi $t0, $t0, 1
  addi $t1, $t1, 1

  lb $t2, 0($t1)
  bne $t2, 0, count2
  move $s1, $t0

# now $s0 == len(str0), $s1 == len(str1)


### change the logic from here

  slt $t0, $s0, $s1
  move $t1, $s1
  beq $t0, 1, saveS0

  j loop

saveS0:
  move $t1, $s0
  j loop

endCompareStrings:
  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  addi $sp, $sp, 4
 
  jr $ra
################################################################################

count:
  lb $t2, 0($t1)
  beq $t2, 0, endcount

  addi $t0, $t0, 1
  addi $t1, $t1, 1

  j count

endcount:
  jr $ra

loop:
  # compare each character. $t1 has the number of iteration left to do
  beq $t1 0 endLoop
  lb $t2, 0($a0)
  lb $t3, 0($a1)

  bne $t2, $t3, compare

  addi $a0, $a0, 1
  addi $a1, $a1, 1
  addi $t1, $t1, -1

  j loop

endLoop:
  move $t2, $s0
  move $t3, $s1

  beq $v0, -1, compare
  j endCompareStrings

compare:
  beq $t2, $t3, endCompareStrings
  slt $t0, $t2, $t3
  beq $t0, 1, v0is0
  j v0is1

v0is0:
  li $v0, 0
  j endLoop

v0is1:
  li $v0, 1
  j endLoop

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

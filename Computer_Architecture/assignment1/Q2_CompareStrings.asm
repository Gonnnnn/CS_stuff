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

  # ret = -1
  li $v0, -1

  # save the length of str0 and str1 on $s0, $s1
  # get len(str0)
  # $t0 = i, $t1 = str0 + i
  li $t0, -1
  move $t1, $a0
  addi $t1, -1

count1:
  addi $t0, $t0, 1
  addi $t1, $t1, 1

  # $t2 = str0[i]
  lb $t2, 0($t1)
  bne $t2, 0, count1

  # $s0 = len(str0)
  move $s0, $t0

  # get len(str1)
  # $t0 = i, $t1 = str1 + i
  li $t0, -1
  move $t1, $a1
  addi $t1, -1
  
count2:
  addi $t0, $t0, 1
  addi $t1, $t1, 1

  # $t2 = str1[i]
  lb $t2, 0($t1)
  bne $t2, 0, count2
  
  # $s1 = len(str1)
  move $s1, $t0

  # now $s0 == len(str0), $s1 == len(str1)
  # check which one is smaller and save it to $t8
  slt $t0, $s0, $s1
  move $t8, $s1
  bne $t0, 1, else
  move $t8, $s0
  
else:
  # $t8 : the shorter length. the number of total interations
  # $t9 : i, index
  li $t9, 0

loop:
  # check if index($t9) is smaller than the total num of iterations($t8)
  slt $t7, $t9, $t8
  # if so, end the loop
  bne $t7, 1, endLoop

  # if not, then
  # compare each character. $t1 has the number of iteration left to do
  # address. $t2 = str0 + i, $ t3 = str1 + i

  add $t2, $a0, $t9
  add $t3, $a1, $t9

  # $t0 = str0[i], $t1 = str1[i]
  lb $t0, 0($t2)
  lb $t1, 0($t3)

  # if theyre different, compare
  bne $t0, $t1, compare

  # if same, increase index
  addi $t9, 1
  j loop

compare:
  # if str0[i] < str1[i], then ret = 0
  slt $t4, $t0, $t1
  beq $t4, 1, v0is0
  # if not, ret = 1. "compare" label is called when str0[i] != str1[i]
  # thus, if str0[i] < str1[i] is false, then definitely str1[i] < str0[i]
  j v0is1

v0is0:
  li $v0, 0
  j endLoop

v0is1:
  li $v0, 1
  j endLoop

endLoop:
  # if ret = -1, compare length
  beq $v0, -1, compareLength
  j endCompareStrings

compareLength:
  slt $t4, $s0, $s1
  beq $t4, 1, v0is0
  slt $t4, $s1, $s0
  beq $t4, 1, v0is1
  j endCompareStrings

endCompareStrings:
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

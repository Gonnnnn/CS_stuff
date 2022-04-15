.data

newline:
  .asciiz "\n"
str0:
  .asciiz "Enter a positive integer: "
str1:
  .asciiz "ERROR: received a negative integer!\n"
str2:
  .asciiz "INFO: fibonacci returned "

.text

################################################################################
# fibonacci
#
# INPUTs
#   $a0: n (<= 32)
#
# OUTPUTs
#   $v0: 0 (if n < 0), 1 (if n >= 0)
#   $v1: F(n) (if n >= 0)
#
fibonacci:
  # stack.push($ra) <-- modify this part depending w.r.t. your implementation
  addi $sp, $sp, -4
  sw $ra, 0($sp)

  # check if the argument is less than zero
  slti $t0, $a0, 0
  li $v0, 0

  # if less than, zero, then return
  beq $t0, 1, return

  # if not, assign 1 on $v1, 0 on $t1, $t2 and go to loop
  # $v1 = F(i), $t1 = F(i-2)
  # $t2 = F(i-1), $t9 = index(i)
  li $v0, 1
  li $v1, 1
  li $t1, 0
  li $t2, 0
  li $t9, 0

loop:
  # check if i is less than $a0
  slt $t0, $t9, $a0
  # if not, then return
  bne $t0, 1, return

  # if not, save the cur $v1 on $t2
  move $t2, $v1
  # and do $v1 = $v1 + $t1 
  add $v1, $v1, $t1
  # renew $t1 with $t2
  move $t1, $t2

  addi $t9, $t9, 1
  j loop

return:
  # $ra = stack.pop() <-- modify this part depending w.r.t. your implementation
  lw $ra, 0($sp)
  addi $sp, $sp, 4

  jr $ra
################################################################################

.globl main
main:

  # stack.push($ra)
  # stack.push($s0)
  addi $sp, $sp, -8
  sw $ra, 0($sp)
  sw $s0, 4($sp)

  # print_string str0
  li $v0, 4
  la $a0, str0
  syscall

  # $t0 = read_int
  li $v0, 5
  syscall
  move $t0, $v0

  # $s0 = $ra; fibonacci($t0); $ra = $s0
  move $s0, $ra
  move $a0, $t0
  jal fibonacci
  move $ra, $s0

  # $t0 = $v0; $t1 = $v1
  move $t0, $v0
  move $t1, $v1

  # if ($t0 == 0) { goto main_failure }
  beq $t0, $zero, main_failure

main_success:

  # print_string str2
  li $v0, 4
  la $a0, str2
  syscall

  # print_int $t1
  li $v0, 1
  move $a0, $t1
  syscall

  # print_string newline
  li $v0, 4
  la $a0, newline
  syscall

  # goto main_return
  b main_return

main_failure:

  # print_string str1
  li $v0, 4
  la $a0, str1
  syscall

main_return:

  # $ra = stack.pop()
  # $s0 = stack.pop()
  lw $ra, 0($sp)
  lw $s0, 4($sp)
  addi $sp, $sp, 8

  # return
  jr $ra

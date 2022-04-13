################################################################################

.data

greet: .asciiz "Hello world\n"
greet1: .asciiz "test1\n"
greet2: .asciiz "test2\n"

################################################################################

.text

test1:
  li $v0, 4
  la $a0, greet1
  syscall

test2:
  li $v0, 4
  la $a0, greet2
  syscall

  jr $ra

main:

  # print_str greet
  li $v0, 4
  la $a0, greet
  syscall

  addi $sp, $sp, -4
  sw $ra, 0($sp)
  jal test1

  lw $ra, 0($sp)
  addi $sp, $sp, 4
  # return
  jr $ra

################################################################################

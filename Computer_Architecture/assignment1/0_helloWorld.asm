################################################################################

.data

greet: .asciiz "Hello world\n"

################################################################################

.text

main:

  # print_str greet
  li $v0, 4
  la $a0, greet
  syscall

  # return
  jr $ra

################################################################################

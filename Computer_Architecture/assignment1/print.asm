printInt:
  addi $sp, $sp, -12
  sw $ra, 0($sp)
  sw $a0, 4($sp)
  sw $v0, 8($sp)

  li $v0 1
  move $a0, [......]
  syscall

  lw $ra, 0($sp)
  lw $a0, 4($sp)
  lw $v0, 8($sp)
  addi $sp, $sp, 12
  jr $ra


printStr:
  addi $sp, $sp, -12
  sw $ra, 0($sp)
  sw $a0, 4($sp)
  sw $v0, 8($sp)

  li $v0 4
  move $a0, [......]
  syscall

  lw $ra, 0($sp)
  lw $a0, 4($sp)
  lw $v0, 8($sp)
  addi $sp, $sp, 12
  jr $ra

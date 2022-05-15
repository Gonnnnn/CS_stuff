#include "MiniMIPS.h"
#include <cstdio>

void MiniMIPS::execInstr() {
  // leave the following line as-is
  unsigned int instr = 0;

  /*
   * implement a piece of code which fetches a 4-byte instruction from the
   * memory using the current value of the PC register and saves it in 'instr'
   */

  unsigned int t;
  for (unsigned int i = 0; i < 4; i++) {
      t = m_PVS.memory[m_PVS.PC + i];
      for (unsigned int j = 0; j < i; j++){
          t *= 256;
      }
      instr += t;
  }

  // leave the following line as-is
  printf("[%s] PC = %u, instr = 0x%08x\n", __func__, m_PVS.PC, instr);

  /*
   * implement the 10 MIPS instructions of interest by:
   *   - decoding the instruction w.r.t. MIPS instruction formats
   *   - performing the operation specified by the instruction
   *   - updating the PC to point to the correct next instruction
   */
  // decode
  unsigned int op = instr >> 26;
  instr -= op << 26;
  unsigned int temp_pc = m_PVS.PC + 4;

  // perform
  // add, sub, and, or, slt
  if (op == 0) {
    unsigned int rs = instr >> 21;
    instr -= rs << 21;
    unsigned int rt = instr >> 16;
    instr -= rt << 16;
    unsigned int rd = instr >> 11;
    instr -= rd << 11;
    unsigned int shamt = instr >> 6;
    instr -= shamt << 6;
    unsigned int funct = instr;

    int rs_val = (int) m_PVS.registers[rs];
    int rt_val = (int) m_PVS.registers[rt];

    if (shamt == 0 && rd != 0) {
      // add
      int temp;
      if (funct == 32) {
        temp = rs_val + rt_val;
      }
      // sub
      else if (funct == 34) {
        temp = rs_val - rt_val;
      }
      // and
      else if (funct == 36) {
        temp = rs_val & rt_val;
      }
      // or
      else if (funct == 37) {
        temp = rs_val | rt_val;
      }
      // slt
      else if (funct == 42) {
        if (rs_val < rt_val) {
          temp = 1;
        }
        else {
          temp = 0;
        }
      }
      // unsigned int result = (unsigned int) temp;
      m_PVS.registers[rd] = (unsigned int) temp;
    }
  }
  // beq, addi, lw, sw
  else if (op == 4 || op == 8 || op == 35 || op == 43) {
    unsigned int rs = instr >> 21;
    instr -= rs << 21;
    unsigned int rt = instr >> 16;
    instr -= rt << 16;
    short imm = instr;
    

    // beq
    if (op == 4) {
      if (m_PVS.registers[rs] == m_PVS.registers[rt]) {
        imm *= 4;
        temp_pc += imm;
      }
    }
    // addi
    else if (op == 8 && rt != 0) {
      int temp = (int) m_PVS.registers[rs];
      temp += imm;
      m_PVS.registers[rt] = (unsigned int) temp;
    }
    // lw
    else if (op == 35 && rt != 0) {
      int start = (int) m_PVS.registers[rs];
      start += imm;

      m_PVS.registers[rt] = 0;
      unsigned int cell;
      for (int i = 0; i < 4; i++) {
        cell = m_PVS.memory[start + i];
        for (unsigned int j = 0; j < i; j++){
          cell *= 256;
        }
        m_PVS.registers[rt] += cell;
      }
    }
    // sw
    else if (op == 43) {
      unsigned int val = m_PVS.registers[rt];
      int start = (int) m_PVS.registers[rs];
      start += imm;
      for (unsigned int i = 0; i < 4; i++) {
        m_PVS.memory[start + i] = val % 256;
        val /= 256;
      }
    }
  }
  // j
  else if (op == 2) {
    unsigned int imm = instr;
    imm *= 4;
    temp_pc = temp_pc >> 28;
    temp_pc = temp_pc << 28;
    temp_pc += imm;
  }

  // update $pc
  m_PVS.PC = temp_pc;
}
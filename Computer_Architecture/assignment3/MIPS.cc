/********************************/
/* Assignment #3: Pipelined CPU */
/********************************/

#include "MIPS.h"

void ALU::advanceCycle() {
  // _ctrlInput을 가지고 어떤 연산을 수행해야하는지를 결정
  // 0000 : AND, 0001 : OR, 0010 : add
  // 0110 : sub, 0111 : set on less than
  // _iInput1, _iInput2를 가지고 해당 연산 수행  
  // _Zero, _oResult를 반환해야할 것

  const unsigned long _ulCtrlInput = _ctrlInput->to_ulong();
  aluZero.reset();

  int ret1 = 0, ret2 = 0, sum = 0, dif = 0, pow = 1;
  for (size_t i = 0; i < 32 - 1; i++) {
    if (_iInput1[i] == 1) { ret1 += pow; }
    if (_iInput2[i] == 1) { ret2 += pow; }
    pow *= 2;
  }
  if (_iInput1[31] == 1) { ret1 -= pow; }
  if (_iInput2[31] == 1) { ret2 -= pow; }

  if (_ulCtrlInput == 0) {
    // AND
    for (size_t i = 0; i < 32; i++) {
      _oResult->set(i, _iInput1[i] && _iInput2[i]);
    }
  } 
  else if (_ulCtrlInput == 1) {
    // OR
    for (size_t i = 0; i < 32; i++) {
      _oResult->set(i, _iInput1[i] || _iInput2[i]);
    }
  }
  else if (_ulCtrlInput == 2) {
    // add
    sum = ret1 + ret2;
    for (int i = 31; i >= 0; --i) {
      _oResult[i] = sum >> i & 1;
    }
  }

  dif = ret1 - ret2;
  if (_ulCtrlInput == 6) {
    // sub
    for (int i = 31; i >= 0; --i) {
    _oResult[i] = dif >> i & 1;
    }
  }
  else if (_ulCtrlInput == 7) {
    // slt
    _oResult->reset();
    if (dif < 0) {
      _oResult->set(0, true);
    }
  }

  BIT aluZero;
  aluZero.set(0, dif == 0);
  _oZero = &aluZero;
}

void PipelinedCPU::doInstructionFetch() {
  // HINT: You should invoke _instMem->advanceCycle() in this method.
  // PC update
  // fetch
  // fetch 결과(oReadData)와 PC + 4값을 IF/ID latch에 저장
  
  // PC update
  // _PCsrc가 0인 경우는 고려 x. 어차피 +4해주는 로직이 아래 있다.
  if (_PCsrc == 1) {
    _PC = _latch_EX_MEM.branchTargetAddr;
  }

  // fetch
  _instMem->advanceCycle();

  // IF/ID latch에 값 update
  _latch_IF_ID.PCPlus4 = {.PCPlut4 = _PC + 4,
                          .inst = _instMem->oReadData};
}

void PipelinedCPU::doInstructionDecode() {
  // HINT: You should invoke _registers->advanceCycle() in this method.
  // decode 진행 후 registerFile 객체에 변수 연결, contorl unit에 op 넘기기
  // _registers->advanceCycle() 진행
  // ID/EX latch에 값 저장

  // decode
  struct {
    std::bitset<6> opcode;
    struct {
      std::bitset<5> rs, rt, rd, shamt;
      std::bitset<6> funct;
    } R;
    struct {
      std::bitset<5> rs, rt;
      std::bitset<16> imm;
    } I;
  } instr_t;

  instr_t ret = {};
  for (size_t i = 0; i < 6; i++)
    ret.opcode[i] = _latch_IF_ID.inst[26 + i];
  for (size_t i = 0; i < 5; i++) {
    ret.R.rs[i] = ret.I.rs[i] = _latch_IF_ID.inst[21 + i];
    ret.R.rt[i] = ret.I.rt[i] = _latch_IF_ID.inst[16 + i];
    ret.R.rd[i] = _latch_IF_ID.inst[11 + i];
    ret.R.shamt[i] = _latch_IF_ID.inst[6 + i];
  }
  for (size_t i = 0; i < 6; i++)
    ret.R.funct[i] = _latch_IF_ID.inst[i];
  for (size_t i = 0; i < 16; i++)
    ret.I.imm[i] = _latch_IF_ID.inst[i];

  // decode 결과를 바탕으로 control unit에 op 넘기기, _registers 변수 변경
  _latch_ID_EX.ctrl_EX = {.RegDst = , .ALUSrc = , .ALUOp = };
  _latch_ID_EX.ctrl_MEM_t = {.Branch = , MemRead = , MemWrite = , ALUZero = };
  _latch_ID_EX.ctrl_WB = {RegWrite = , MemToReg = };

  for (size_t i = 0; i < 5; i++) {
    _registersIReadRegister1[i] = ret.R.rs[i];
    _registersIReadRegister2[i] = ret.R.rt[i];
    _registersIWriteData[i] = ret.R.rd[i];
  }


  _registers->advanceCycle();

}

void PipelinedCPU::doExecute() {
  /* FIXME */
  // HINT: You should invoke _ALU->advanceCycle() in this method.
}

void PipelinedCPU::doDataMemoryAccess() {
  /* FIXME */
  // HINT: You should invoke _dataMem->advanceCycle() in this method.
}

void PipelinedCPU::doWriteBack() {
  /* FIXME */
}
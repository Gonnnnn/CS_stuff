/********************************/
/* Assignment #3: Pipelined CPU */
/********************************/

#include "MIPS.h"

// _registersCtrlRegWrite 이거 어쩔지 생각해야한다.

// doExecute만 돌아가면 IF가 난리난다. 둘 관련해서 생각. 왜 오류가 날까

void ALU::advanceCycle() {
  std::cout << "ALU start" << std::endl;
  // _ctrlInput을 가지고 어떤 연산을 수행해야하는지를 결정
  // 0000 : AND, 0001 : OR, 0010 : add
  // 0110 : sub, 0111 : set on less than
  // _iInput1, _iInput2를 가지고 해당 연산 수행  
  // _Zero, _oResult를 반환해야할 것

  // _ALU = new ALU(&_latch_ID_EX.registersReadData1, &_ALUInput1,
  //     &_latch_EX_MEM.ctrl_MEM.ALUZero, &_latch_EX_MEM.ALUResult,
  //     &_ALUCtrlInput);

  const unsigned long _ulCtrlInput = _ctrlInput->to_ulong();

  int ret1 = int(_iInput1->to_ulong());
  int ret2 = int(_iInput2->to_ulong());
  int sum = 0, dif = 0;
  // for (size_t i = 0; i < 32 - 1; i++) {
  //   if (_iInput1[i] == 1) { ret1 += pow; }
  //   if (_iInput2[i] == 1) { ret2 += pow; }
  //   pow *= 2;
  // }
  // if (_iInput1[31] == 1) { ret1 -= pow; }
  // if (_iInput2[31] == 1) { ret2 -= pow; }

// 위 로직 확인

  std::cout << "_iInput1: " << ret1 << std::endl;
  std::cout << "_iInput2: " << ret2 << std::endl;

  if (_ulCtrlInput == 0) {
    // AND
    std::cout << "ALU AND" << std::endl;
    *_oResult = (*_iInput1) & (*_iInput2);
  } 
  else if (_ulCtrlInput == 1) {
    // OR
    std::cout << "ALU OR" << std::endl;
    *_oResult = (*_iInput1) | (*_iInput2);
  }
  else if (_ulCtrlInput == 2) {
    // add
    std::cout << "ALU ADD" << std::endl;
    sum = ret1 + ret2;
    for (int i = 31; i >= 0; --i) {
      (*_oResult)[i] = sum >> i & 1;
    }
  }

  dif = ret1 - ret2;
  if (_ulCtrlInput == 6) {
    // sub
    std::cout << "ALU SUB" << std::endl;
    for (int i = 31; i >= 0; --i) {
      (*_oResult)[i] = dif >> i & 1;
    }
  }
  else if (_ulCtrlInput == 7) {
    // slt
    std::cout << "ALU SLT" << std::endl;
    _oResult->reset();
    if (dif < 0) {
      _oResult->set(0, true);
    }
  }
  std::cout << "ALU dif: "<< dif << std::endl;
  _oZero->set(0, dif == 0);
}

void PipelinedCPU::doInstructionFetch() {
  std::cout << "IF start" << std::endl;
  // HINT: You should invoke _instMem->advanceCycle() in this method.
  // PC update
  // fetch
  // fetch 결과(oReadData)와 PC + 4값을 IF/ID latch에 저장
  
  // PC update
  if (_PCSrc.all()) {
    _PC = _latch_EX_MEM.branchTargetAddr;
  }
  else {
    _PC = _PC.to_ulong() + 4;
  }

  // fetch
  _instMem->advanceCycle();

  // IF/ID latch에 값 update. inst는 advanceCycle에서 update된다.
  _latch_IF_ID.PCPlus4 = _PC.to_ulong() + 4;
  std::cout << "IF end" << std::endl;
}

void PipelinedCPU::doInstructionDecode() {
  std::cout << "ID start" << std::endl;
  // HINT: You should invoke _registers->advanceCycle() in this method.
  // parse 진행 후 registerFile 객체에 변수 연결 (wire)
  // _registers->advanceCycle() 진행
  // ID/EX latch에 값 저장

  // parse
  struct instr_t {
    std::bitset<6> opcode;
    struct {
      std::bitset<5> rs, rt, rd;
    }R;
    struct {
      std::bitset<16> imm;
    }I;
  };

  instr_t ret = {};
  for (size_t i = 0; i < 6; i++)
    ret.opcode[i] = _latch_IF_ID.inst[26 + i];
  for (size_t i = 0; i < 5; i++) {
    ret.R.rs[i] = _latch_IF_ID.inst[21 + i];
    ret.R.rt[i] = _latch_IF_ID.inst[16 + i];
    ret.R.rd[i] = _latch_IF_ID.inst[11 + i];
  }
  for (size_t i = 0; i < 16; i++)
    ret.I.imm[i] = _latch_IF_ID.inst[i];

  std::cout << "rs: " << ret.R.rs.to_ulong() << std::endl;
  std::cout << "rt: " << ret.R.rt.to_ulong() << std::endl;
  std::cout << "rd: " << ret.R.rd.to_ulong() << std::endl;


  // target register들 idx을 wire에 update
  _registersIReadRegister1 = ret.R.rs;
  _registersIReadRegister2 = ret.R.rt;

  // Read data
  // 포인터로 연결해서 latch의 registersReadData1,2는 알아서 update된다.
  _registers->advanceCycle();

  // ID_Ex_latch에 update해야하는 나머지 값들 update
  _latch_ID_EX.PCPlus4 = _latch_IF_ID.PCPlus4;

  if (ret.I.imm[15] == 0) {
    _latch_ID_EX.signExtdImm.reset();
  }
  else if (ret.I.imm[15] == 1) {
    _latch_ID_EX.signExtdImm.set();
  }

  for (size_t i = 0; i < 16; i++) {
    _latch_ID_EX.signExtdImm[i] = ret.I.imm[i];
  }
  
  _latch_ID_EX.inst_20_16 = ret.R.rt;
  _latch_ID_EX.inst_15_11 = ret.R.rd;

  // ctrl signals update

  // add, sub, and, or, slt : opcode 0
  // addi : opcode 8
  // lw, sw : 35, 43
  // beq : 4

  // EX : RegDst, ALUSrc, ALUOp
  // MEM : Branch, MemRead, MemWrite, ALUZero
  // WB : RegWrite, MemToReg
  switch (ret.opcode.to_ulong()) {
    case 0: {
      // add, sub, and, or, slt
      // ALUOp = 10
      _latch_ID_EX.ctrl_EX = {.RegDst = 1, .ALUSrc = 0, .ALUOp = 2};
      // ALUZero는 임시로 0
      _latch_ID_EX.ctrl_MEM = {.Branch = 0, .MemRead = 0, .MemWrite = 0, .ALUZero = 0};
      _latch_ID_EX.ctrl_WB = {.RegWrite = 1, .MemToReg = 0};
      break;
    }
    case 8: {
      // addi
      // addi는 ALUOp이 00이도록 설정
      // destination은 rt -> RegDst = 0
      // imm을 read해야하기에 ALUSrc = 1
      _latch_ID_EX.ctrl_EX = {.RegDst = 0, .ALUSrc = 1, .ALUOp = 0};
      // ALUZero는 임시로 0
      _latch_ID_EX.ctrl_MEM = {.Branch = 0, .MemRead = 0, .MemWrite = 0, .ALUZero = 0};
      _latch_ID_EX.ctrl_WB = {.RegWrite = 1, .MemToReg = 0};
      break;
    }
    case 35: {
      // lw
      _latch_ID_EX.ctrl_EX = {.RegDst = 0, .ALUSrc = 1, .ALUOp = 0};
      // ALUZero는 임시로 0
      _latch_ID_EX.ctrl_MEM = {.Branch = 0, .MemRead = 1, .MemWrite = 0, .ALUZero = 0};
      _latch_ID_EX.ctrl_WB = {.RegWrite = 1, .MemToReg = 1};
      break;
    }
    case 43: {
      // sw
      _latch_ID_EX.ctrl_EX = {.RegDst = 0, .ALUSrc = 1, .ALUOp = 0};
      // ALUZero는 임시로 0
      _latch_ID_EX.ctrl_MEM = {.Branch = 0, .MemRead = 0, .MemWrite = 1, .ALUZero = 0};
      _latch_ID_EX.ctrl_WB = {.RegWrite = 0, .MemToReg = 0};
      break;
    }
    case 4: {
      // beq
      // ALUOp = 01
      _latch_ID_EX.ctrl_EX = {.RegDst = 0, .ALUSrc = 0, .ALUOp = 1};
      // ALUZero는 임시로 0
      _latch_ID_EX.ctrl_MEM = {.Branch = 1, .MemRead = 0, .MemWrite = 0, .ALUZero = 0};
      _latch_ID_EX.ctrl_WB = {.RegWrite = 0, .MemToReg = 0};
      break;
    }
  }
  std::cout << "ID end" << std::endl;
}

void PipelinedCPU::doExecute() {
  std::cout << "EX start" << std::endl;
  // HINT: You should invoke _ALU->advanceCycle() in this method.
  // _ALU = new ALU(&_latch_ID_EX.registersReadData1, &_ALUInput1,
  //     &_latch_EX_MEM.ctrl_MEM.ALUZero, &_latch_EX_MEM.ALUResult,
  //     &_ALUCtrlInput);

  
  std::bitset<6> funct;
  for (size_t i = 0; i < 6; i++)
    funct[i] = _latch_ID_EX.signExtdImm[i];

  // _latch_ID_EX.ctrl_EX.ALUOp과 _latch_ID_EX.signExtdImm으로 _ALUCtrlInput 갱신
  // _ALUCtrlInput (wire)
  switch (_latch_ID_EX.ctrl_EX.ALUOp.to_ulong()) {
    case 2: {
      //R type
      funct = funct << 2;
      funct = funct >> 2;
      switch (funct.to_ulong()) {
        case 0: {
          _ALUCtrlInput = 2;
          break;
        }
        case 2: {
          _ALUCtrlInput = 6;
          break;
        }
        case 4: {
          _ALUCtrlInput = 0;
          break;
        }
        case 5: {
          _ALUCtrlInput = 1;
          break;
        }
        case 6: {
          _ALUCtrlInput = 7;
          break;
        }
      }
      break;
    }
    case 1: {
      // beq
      _ALUCtrlInput = 6;
      break;
    }
    case 0: {
      // lw, sw, addi
      _ALUCtrlInput = 2;
      break;
    }
  }

  // _latch_ID_EX.ctrl_EX.ALUSrc로 두번째 input data 판단
  // _ALUInput1 (wire)
  if (_latch_ID_EX.ctrl_EX.ALUSrc[0] == 0) {
    _ALUInput1 = _latch_ID_EX.registersReadData2;
  }
  else if (_latch_ID_EX.ctrl_EX.ALUSrc[0] == 1) {
    _ALUInput1 = _latch_ID_EX.signExtdImm;
  }
  std::cout << "_latch_ID_EX.registersReadData1: "<< _latch_ID_EX.registersReadData1 << std::endl;
  std::cout << "_ALUInput1: "<< _ALUInput1 << std::endl;

  // 연산
  _ALU->advanceCycle();

  // _latch_EX_MEM udpate
  int temp_PC = 0, temp_imm = 0, sum = 0, pow = 1;
  WORD tempSignExtdImm = _latch_ID_EX.signExtdImm << 2;
  for (size_t i = 0; i < 32 - 1; i++) {
    if (_latch_ID_EX.PCPlus4[i] == 1) { temp_PC += pow; }
    if (tempSignExtdImm[i] == 1) { temp_imm += pow; }
    pow *= 2;
  }
  if (_latch_ID_EX.PCPlus4[31] == 1) { temp_PC -= pow; }
  if (tempSignExtdImm[31] == 1) { temp_imm -= pow; }
  sum = temp_PC + temp_imm;
  for (int i = 31; i >= 0; --i) {
    // _latch_EX_MEM.branchTargetAddr
    _latch_EX_MEM.branchTargetAddr[i] = sum >> i & 1;
  }
  // _latch_EX_MEM.registersReadData2
  _latch_EX_MEM.registersReadData2 = _latch_ID_EX.registersReadData2;
  // _latch_EX_MEM.writeRegForRegisters
  if (_latch_ID_EX.ctrl_EX.RegDst[0] == 0) {
    _latch_EX_MEM.writeRegForRegisters = _latch_ID_EX.inst_20_16;
  }
  else if (_latch_ID_EX.ctrl_EX.RegDst[0] == 1) {
    _latch_EX_MEM.writeRegForRegisters = _latch_ID_EX.inst_15_11;
  }
  
  // ALUZero를 제외한 _latch_EX_MEM.ctrl_MEM. ALUZero는 advanceCycle돌리면서 계산이 된다.
  // 또한 포인터로 이어져있어 업데이트 됨
  _latch_EX_MEM.ctrl_MEM.Branch = _latch_ID_EX.ctrl_MEM.Branch;
  _latch_EX_MEM.ctrl_MEM.MemRead = _latch_ID_EX.ctrl_MEM.MemRead;
  _latch_EX_MEM.ctrl_MEM.MemWrite = _latch_ID_EX.ctrl_MEM.MemWrite;

  // _latch_EX_MEM.ctrl_WB
  _latch_EX_MEM.ctrl_WB = _latch_ID_EX.ctrl_WB;

  std::cout << "EX end" << std::endl;
}

void PipelinedCPU::doDataMemoryAccess() {
  std::cout << "MEM start" << std::endl;
  // HINT: You should invoke _dataMem->advanceCycle() in this method.
  // _dataMem = new Memory(dataMemSize, &_latch_EX_MEM.ALUResult,
  //     &_latch_EX_MEM.registersReadData2, &_latch_MEM_WB.dataMemReadData,
  //     &_latch_EX_MEM.ctrl_MEM.MemRead, &_latch_EX_MEM.ctrl_MEM.MemWrite);
  
  // PCSrc update
  _PCSrc = _latch_EX_MEM.ctrl_MEM.ALUZero & _latch_EX_MEM.ctrl_MEM.Branch;
  // Read/Write
  // MemRead, MemWrite는 윗단에서 정해진게 포인터로 이어져 들어간다.
  _dataMem->advanceCycle();

  // _latch_MEM_WB update. dataMemReadData는 포인터로 알아서 이어진다.
  _latch_MEM_WB.ALUResult = _latch_EX_MEM.ALUResult;
  _latch_MEM_WB.writeRegForRegisters = _latch_EX_MEM.writeRegForRegisters;
  _latch_MEM_WB.ctrl_WB = _latch_EX_MEM.ctrl_WB;

  std::cout << "MEM end" << std::endl;
}

void PipelinedCPU::doWriteBack() {
  std::cout << "WB start" << std::endl;
  // MemtoReg를 기준으로 _registersIWriteData 선정
  if (_latch_MEM_WB.ctrl_WB.MemToReg[0] == 0) {
    _registersIWriteData = _latch_MEM_WB.ALUResult;
  }
  else if (_latch_MEM_WB.ctrl_WB.MemToReg[0] == 1) {
    _registersIWriteData = _latch_MEM_WB.dataMemReadData;
  }
  _registersCtrlRegWrite = _latch_MEM_WB.ctrl_WB.RegWrite;
  _registersIWriteRegister = _latch_MEM_WB.writeRegForRegisters;
  std::cout << "WB end" << std::endl;
}
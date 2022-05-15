#include "MiniMIPS.h"
#include <cstdlib>
#include <cstdio>
#include <string>

int main(int argc, char **argv) {
  if (argc != 4) {
    fprintf(stderr, "Usage: %s dataFile initialPC numInstrs\n", argv[0]);
    exit(-1);
  }

  const char *dataFile = argv[1];
  const unsigned int initialPC = std::stoul(argv[2]);
  const unsigned int numInstrs = std::stoul(argv[3]);

  MiniMIPS *cpu = new MiniMIPS(dataFile, initialPC);
  for (unsigned int i = 0; i < numInstrs; i++) {
    cpu->execInstr();
  }
  
  // +-- modify as needed!
  // |
  // v
  cpu->printPC();         // print $pc
  cpu->printRegister(8);  // print $t0
  cpu->printRegister(9);  // print $t1
  cpu->printRegister(10); // print $t2
  cpu->printRegister(11); // print $t3
  cpu->printMemory(264);  // print memory[267:264]
  // ^
  // |
  // +-- modify as needed!
  
  delete cpu;

  return 0;
}


#include "Cache.h"

// '_memory' defined in testCache.cc
extern BYTE _memory[MEMSIZE];

/*************************************************************************************/
/* Assignment #4: Caches                                                             */
/*                                                                                   */
/* addr: target memory address                                                       */
/* isWrite: true if write, false if read                                             */
/* data: data to be written to addr if isWrite, data read from the cache if !isWrite */
/*************************************************************************************/
void Cache::access(const WORD addr, const bool isWrite, WORD *data) {
  assert(addr % 4 == 0);
  assert(data != nullptr);

  // FIXME
}


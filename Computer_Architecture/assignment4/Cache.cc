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

  /*************************************************************************************/
  size_t temp;
  // index bit size 확인
  size_t index_length = 0;
  temp = _numSets;
  while(true){
    if(temp == 1){
      break;
    }
    temp = temp >> 1;
    index_length += 1;
  }

  // offset bit size 확인
  size_t offset_length = 0;
  temp = _lineSize;
  while(true){
    if(temp == 1){
      break;
    }
    temp = temp >> 1;
    offset_length += 1;
  }
  /*************************************************************************************/

  /*************************************************************************************/
  // tag, index, offset 구하기
  WORD line_address = addr >> offset_length;
  size_t offset = addr - (line_address << offset_length);
  WORD tag = line_address >> index_length;
  size_t index = line_address - (tag << index_length) ;
  /*************************************************************************************/

  /*************************************************************************************/
  // 해당 set내의 valid bit들의 counter 1씩 올리기
  // valid라면 tag확인해서 hit 여부 판단하기, 또한 LRU policy에 따라, 가장 counter가 높은 index 구하기
  // invalid라면 invalid한 애가 있음을 표기하고, 해당 invalid의 index구하기. 여러개라면 그 중 index 가장 작은 것
  size_t target = 0;
  size_t invalid_victim = 0;
  size_t lru_victim = 0;
  size_t lru_max = 0;
  size_t victim;
  bool hit = false;
  bool invalid_bit = false;
  for(int i=_numLines/_numSets-1; i >= 0 ; i--){
    if(_lines[index][i].valid == true){
      if(lru_max <= _lines[index][i].lruCounter){
        lru_max = _lines[index][i].lruCounter;
        lru_victim = i;
      }
      _lines[index][i].lruCounter += 1;
      if(_lines[index][i].tag == tag){
        hit = true;
      }
    }
    else{
      invalid_bit = true;
      invalid_victim = i;
    }
  }
  /*************************************************************************************/

  /*************************************************************************************/
  // write request
  if(isWrite){
    if(hit){
      for(size_t i = 0; i < 4; i++){
        _lines[index][target].data[offset + i] = (*data % 256);
        if(_writePolicy == WRITE_THROUGH){
          _memory[addr + i] = (*data % 256);
        }
        *data >>= 8;
      }
      if(_lines[index][target].dirty && _writePolicy == ){

      }
      if(_writePolicy == WRITE_BACK){
        if(_lines[index][target].dirty){


        }else{
          _lines[index][target].dirty = true;
        }
      }
    }
    else{
      // miss여도 victim 찾아서 바로 memory에서 값 가져와 write해버림
      if(invalid_bit){
        // invalid_bit이 존재할 경우
        victim = invalid_victim;
        _lines[index][victim].valid = true;
      }else{
        // invalid_bit이 없을 경우. LRU policy를 따른다.
        victim = lru_victim;
      }
      if(_writePolicy == WRITE_THROUGH){
        // update line
        _lines[index][victim].tag = tag;
        // hit이 아니므로, memory에서 line를 가져온다.
        for(size_t i=0; i < 4; i++){
          _lines[index][victim].data[offset + i] = (*data % 256);
          *data >>= 8;
        }
        _lines[index][victim].lruCounter = 0;



      // write_back 타입인 경우 dirty값 갱신
      if(_writePolicy == WRITE_BACK){
        _lines[index][victim].dirty = false;
      }



        
        
      }
      

    }
  }
  /*************************************************************************************/
  /*************************************************************************************/
  // read request
  else{
    if(hit != true){
      if(invalid_bit){
        // invalid_bit이 존재할 경우
        victim = invalid_victim;
        _lines[index][victim].valid = true;
      }else{
        // invalid_bit이 없을 경우. LRU policy를 따른다.
        victim = lru_victim;
      }
      // dirty가 true이면, 먼저 해당 line를 memory에 update시켜주고나서 cache를 바꿔야할 것이다.
      // dirty도 false로 갱신
      if(_writePolicy == WRITE_BACK && _lines[index][victim].dirty){
        WORD dirty_addr = (_lines[index][victim].tag << (index_length + offset_length)) + (index << offset_length) + offset;
        for(size_t i=0; i < _lineSize; i++){
          _memory[dirty_addr + i] = _lines[index][victim].data[i];
        }
        _lines[index][victim].dirty = false;
      }

      // update cahche line
      _lines[index][victim].tag = tag;
      // hit이 아니므로, memory에서 line를 가져온다.
      for(size_t i=0; i < _lineSize; i++){
        _lines[index][victim].data[i] = _memory[addr + i];
      }
      _lines[index][victim].lruCounter = 0;

      target = victim;
    }
    // cache 정리 끝. 실제 data(1word) 가져오기
    *data = 0;
    for(size_t i = 0; i < 4; i++){
      *data <<= 8;
      *data += _lines[index][target].data[offset + (3 - i)];
    }
  }
  /*************************************************************************************/
}
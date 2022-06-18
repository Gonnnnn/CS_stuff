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
  // _numLines, _numSets, _lineSize 이용해서 cache 특성 고려, addr parsing
  // tag, index, byte_offset으로 parsing하면 됨

  size_t temp;
  size_t index_length = 0;
  temp = _numSets;
  while(true){
    if(temp == 1){
      break;
    }
    temp = temp >> 1;
    index_length += 1;
  }
  size_t offset_length = 0;
  temp = _lineSize;
  while(true){
    if(temp == 1){
      break;
    }
    temp = temp >> 1;
    offset_length += 1;
  }

  // tag, index, a 구하기
  WORD line_address = addr >> offset_length;
  size_t offset = addr - (line_address << offset_length);
  WORD tag = line_address >> index_length;
  size_t index = line_address - (tag << index_length) ;

  // 해당 set내의 valid bit들 counter 1씩 올리기
  // valid라면 tag확인해서 hit 여부 판단하기, 또한 LRU policy에 따라, 가장 counter가 높은 index 구하기. 가장 높은 것이 여러개면 그 중 index 가장 작은 것
  // invalid라면 invalid한 애가 있음을 표기하고, 해당 invalid의 index구하기. 여러개라면 그 중 index 가장 작은 것
  int target = -1;
  int invalid_victim = -1;
  int lru_victim = -1;
  size_t lru_max = 0;
  int victim;
  bool hit = false;
  bool invalid_bit = false;
  for(int i=_numLines/_numSets-1; i >= 0 ; i--){
    if(_lines[index][i].valid == true){
      // lrc_victim을 이렇게 구하는게 맞는지 생각해봐야함. counter개수가 같은 애들 중 index가 가장 작은 애를 고르는게 맞겠지?
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

  if(isWrite){
    if(hit){
      if(_writePolicy == WRITE_THROUGH){
        // update cache and memory at the same time(사실 동시에는 아니고, 순차적으로 진행되어야하긴 함)
        for(size_t i = 0; i < 4; i++){
          _lines[index][target].data[offset + i] = (*data % 256);
          _memory[addr + i] = (*data % 256);
          *data >>= 8;
        }
      }
      // else{
        

      // }


    }
    // 6/17 write에서 miss가 났을때 write에서 hit인데 write back policy일 때를 구현해야한다.
    else{
      if(invalid_bit){
        // invalid_bit이 존재할 경우
        victim = invalid_victim;
      }else{
        // invalid_bit이 없을 경우. LRU policy를 따른다.
        victim = lru_victim;
      }
      // update line
      _lines[index][victim].valid = true;
      _lines[index][victim].tag = tag;
      // hit이 아니므로, memory에서 data를 가져온다.
      for(size_t i=0; i < 4; i++){
        _lines[index][victim].data[offset + i] = (*data % 256);
        *data >>= 8;
      }
      _lines[index][victim].lruCounter = 0;



      // write_back 타입인 경우 dirty값 갱신
      if(_writePolicy == WRITE_BACK){
        _lines[index][victim].dirty = false;
      }



      
      _lines[index][target].valid = true;

    }
  }else{
    if(hit != true){
      if(invalid_bit){
        // invalid_bit이 존재할 경우
        victim = invalid_victim;
        _lines[index][victim].valid = true;
      }else{
        // invalid_bit이 없을 경우. LRU policy를 따른다.
        victim = lru_victim;
      }
      // update line
      _lines[index][victim].tag = tag;
      // hit이 아니므로, memory에서 data를 가져온다.
      // 아마 cache도 little endian이겠지
      for(size_t i=0; i < 4; i++){
        // 여기서 offset 안썼다고함 태헌이는
        _lines[index][victim].data[offset + (3 - i)] = _memory[addr + (3 - i)];
      }
      _lines[index][victim].lruCounter = 0;
      // write_back 타입인 경우 dirty값 갱신
      if(_writePolicy == WRITE_BACK){
        _lines[index][victim].dirty = false;
      }

      target = victim;
    }
    *data = 0;
    for(size_t i = 0; i < 4; i++){
      *data <<= 8;
      *data += _lines[index][target].data[offset + (3 - i)];
    }
  }
}
// 어차피 4byte씩 적고 쓰니까, byte offset보다는 line내에 word가 어디 위치하는지를 아는게 중요할듯?
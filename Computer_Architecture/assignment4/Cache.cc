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
    temp = temp >> 2;
    index_length += 1;
  }
  size_t offset_length = 0;
  temp = _lineSize;
  while(true){
    if(temp == 1){
      break;
    }
    temp = temp >> 2;
    offset_length += 1;
  }

  // tag, index, offset 구하기
  WORD line_address = addr >> offset_length;
  siez_t offset = addr - (line_address << offset_length);
  WORD tag = line_address >> index_length;
  size_t index = line_address - (tag << index_length) ;

  // 해당 set내의 valid bit들 counter 1씩 올리기
  // valid라면 tag확인해서 hit 여부 판단하기, 또한 LRU policy에 따라, 가장 counter가 높은 index 구하기. 가장 높은 것이 여러개면 그 중 index 가장 작은 것
  // invalid라면 invalid한 애가 있음을 표기하고, 해당 invalid의 index구하기. 여러개라면 그 중 index 가장 작은 것
  int target = -1;
  int invalid_victim = -1;
  int lru_victim = -1;
  int lru_max = -1;
  int victim;
  bool hit = false;
  bool invalid_bit = false;
  for(int i=_numLines/_numSets-1; i >= 0 ; i--){
    if(_lines[index][i].valid == true){
      // lrc_victim을 이렇게 구하는게 맞는지 생각해봐야함. counter개수가 같은 애들 중 index가 가장 작은 애를 고르는게 맞겠지?
      if(lru_max < _lines[index][i].lruCounter){
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

    }else{

    }
  }else{
    if(hit){
      *data = 0;
      // 여기 다시 생각해볼필요
      for(int i = 0; i < _lineSize; i++){
        *data <<= 8;
        *data += _lines[index][target].data[offset + 3 - i];
      }
    }else{
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
      // 아마 cache도 little endian이겠지
      for (int i=0; i < 4; i++){
        // 여기서 offset 안썼다고함 태헌이는
        _lines[index][victim].data[offset + (3 - i)] = _memory[addr + (3 - i)];
      }
      _lines[index][victim].lruCounter = 0;
      // write_back 타입인 경우 dirty값 갱신
      if(_writePolicy == WRITE_BACK){
        _lines[index][victim].dirty = false;
      }
    }
  }



  // isWrite이 false일 때
  // 해당 block을 찾아간 후, tag 확인. 같다면 읽어서 *data에 넣기
  // 다르다면 memory에서 불러오기. 불러온 후 dirty = False 처리

  // isWrite이 true일 때
  // 해당 block을 찾아간 후, tag 확인
  // tag가 맞다면, i
}
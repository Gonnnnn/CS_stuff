

public final class BoardSolver implements IBoardSolver {
    private int width;
    private int height;

    @Override
    public IBoard solve(IBoard board) {
        /*
         * Function input:
         *  + board: A board filled with CellState.EMPTY.
         *
         * Job:
         *  Return an answer board.
         *  If there are more than one answer, you can choose one of them.
         *  If there is no answer, return null.
         *
         * IBoard has following methods:
         *  int getWidth(): Returns the width of the board.
         *  int getHeight(): Returns the height of the board.
         *
         *  int[] getColumnConstraints(int c): Returns constraints for column #c.
         *  int[] getRowConstraints(int r): Returns constraints for row #r.
         *
         *  void fill(int r, int c): Fill the cell (#r, #c)
         *  void erase(int r, int c): Erase the cell (#r, #c)
         *  CellState getCellState(int r, int c):
         *    Returns the state of the cell (#r, #c)
         *     - CellState.FILLED: the cell is filled
         *     - CellState.EMPTY: the cell is empty
         *
         */
         this.width = board.getWidth();
         this.height = board.getHeight();

         if (recursive_checker(0, 0, board)) {
           return board;
         }

         return null;
    }


    public int nextXcord(int x){
      return (x + 1) % this.width;
    }


    public int nextYcord(int y, int x){
      if(x + 1 == this.width){
        return y + 1;
      }
      return y;
    }


    public boolean recursive_checker(int y, int x, IBoard board){
      if (y == this.height){
        return true;
      }

      int x_plus_1 = nextXcord(x);
      int y_plus_1 = nextYcord(y, x);
      boolean column_check;
      boolean row_check;
      boolean valid;

      board.fill(y, x);

      column_check = is_this_column_valid(x, y, this.height, board.getColumnConstraints(x), board);
      row_check = is_this_row_valid(y, x, this.width, board.getRowConstraints(y), board);
      if (column_check && row_check){
        valid = true;
      }else{
        valid = false;
      }
      if (valid && recursive_checker(y_plus_1, x_plus_1, board)) {
        return true;
      }

      board.erase(y, x);

      column_check = is_this_column_valid(x, y, this.height, board.getColumnConstraints(x), board);
      row_check = is_this_row_valid(y, x, this.width, board.getRowConstraints(y), board);
      if (column_check && row_check){
        valid = true;
      }else{
        valid = false;
      }
      if (valid && recursive_checker(y_plus_1, x_plus_1, board)) {
        return true;
      }

      return false;
    }


    public boolean is_this_column_valid(int which_column, int index, int total_length, int[] cons, IBoard board) {

      int nth_con = 0;
      int count = 0;
      boolean was_one = false;
      boolean filled = true;
      boolean all_empty = true;

      for(int i = 0; i <= index; i++){
        if(board.getCellState(i, which_column) == CellState.FILLED){
          all_empty = false;
          break;
        }
      }

      if(cons == null && all_empty){
        return true;
      }

      for(int i = 0; i <= index; i++){
        if(board.getCellState(i, which_column) == CellState.FILLED){
          if(!was_one){
            if(nth_con == cons.length){
              return false;
            }
          }

          count++;
          was_one = true;

        }else if(board.getCellState(i, which_column) == CellState.EMPTY){
          if(was_one){
            if(cons[nth_con] != count){
              return false;
            }
            nth_con++;
          }
          count = 0;
          was_one = false;

        }
      }

      if(index + 1 == total_length){
        if(was_one){
          return nth_con + 1 == cons.length && cons[nth_con] == count;
        }else{
          return nth_con == cons.length;
        }
      }
      //if it ended with an empty cell, and if it's correct, nth_con will be equal to cons.length

      //if it ended with an filled cell, and if it's correct, nth_con will be cons.length - 1.
      //since we didnt compare the cons with the count, we have to compare it too
      return true;
    }


    public boolean is_this_row_valid(int which_row, int index, int total_length, int[] cons, IBoard board) {

      int nth_con = 0;
      int count = 0;
      boolean was_one = false;
      boolean filled = true;
      boolean all_empty = true;

      for(int i = 0; i <= index; i++){
        if(board.getCellState(which_row, i) == CellState.FILLED){
          all_empty = false;
        }
      }

      if(cons == null && all_empty){
        return true;
      }

      for(int i = 0; i <= index; i++){
        if(board.getCellState(which_row, i) == CellState.FILLED){
          if(!was_one){
            if(nth_con == cons.length){
              return false;
            }
          }

          count++;
          was_one = true;

        }else if(board.getCellState(which_row, i) == CellState.EMPTY){
          if(was_one){
            if(cons[nth_con] != count){
              return false;
            }
            nth_con++;
          }
          count = 0;
          was_one = false;

        }
      }

      if(index + 1 == total_length){
        if(was_one){
          return nth_con + 1 == cons.length && cons[nth_con] == count;
        }else{
          return nth_con == cons.length;
        }
      }
      //if it ended with an empty cell, and if it's correct, nth_con will be equal to cons.length

      //if it ended with an filled cell, and if it's correct, nth_con will be cons.length - 1.
      //since we didnt compare the cons with the count, we have to compare it too
      return true;
    }
}

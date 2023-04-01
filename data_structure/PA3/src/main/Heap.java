/*
* Name: Kim Geon
* Student ID #:
*/

/*
* Do NOT import any additional packages/classes.
* If you (un)intentionally use some additional packages/classes we did not
* provide, you may receive a 0 for the homework.
*/

/* Min Heap stores entry with integer key and arbitrary type value */
public final class Heap<V> implements IHeap<V> {
    /*
    * Use some variables for your implementation.
    */
    private INode<V> root;
    private INode<V> lastNode;
    private int size;

    private int height;
    private int num_of_nodes_except_bottom;
    private int num_of_bottom_nodes;
    private int num_of_bottom_nodes_completed;
    private int current_idx;

    public Heap() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        this.root = null;
        this.lastNode = null;
        this.size = 0;
    }

    @Override
    public void bottomUp(int[] keys, V[] values) throws IllegalStateException {
        /*
        * Function input:
        *  keys: keys of entries
        *  values: values of entries
        *
        * Job:
        *  Construct a heap with entries (keys[i], values[i]).
        *  You *must* construct a heap using *bottom-up construction*.
        *  If the heap is not empty or lengths of keys and values are different,
        *  throw an exception.
        */
        if ((keys.length != values.length) || (!this.isEmpty())){
          throw new IllegalStateException();
        }

        this.size = keys.length;

        if(this.size == 1){
          INode current_node = new Node(keys[0], values[0]);
          this.root = current_node;
          this.lastNode = this.root;
          return;
        }


        this.height = 0;
        this.num_of_nodes_except_bottom = 1;
        while(true){
          if((this.num_of_nodes_except_bottom <= this.size) && (this.size <= num_of_nodes_except_bottom * 2 - 1)){
            break;
          }
          this.height += 1;
          this.num_of_nodes_except_bottom *= 2;
        }
        this.num_of_nodes_except_bottom -= 1;
        this.num_of_bottom_nodes = this.size - this.num_of_nodes_except_bottom;



        this.num_of_bottom_nodes_completed = 0;
        this.current_idx = 0;
        this.root = construct_and_merge(0, keys, values);

        return;
    }

    public INode<V> construct_and_merge(int level, int[] keys, V[] values){
      // -------------------------------------------
      if((level == this.height) && (this.num_of_bottom_nodes_completed == this.num_of_bottom_nodes)){
        return null;
      }
      // -------------------------------------------
      // it can be removed and it'll still work the same way

      INode current_node = new Node(keys[this.current_idx], values[this.current_idx]);
      this.current_idx++;

      if((level == this.height) && (this.num_of_bottom_nodes_completed < this.num_of_bottom_nodes)){
        this.num_of_bottom_nodes_completed++;
        if(this.num_of_bottom_nodes_completed == this.num_of_bottom_nodes){
          this.lastNode = current_node;
        }
        return current_node;
      }

      if((level == this.height - 1) && (this.num_of_bottom_nodes_completed == this.num_of_bottom_nodes)){
        return current_node;
      }

      current_node.setLeft(construct_and_merge(level + 1, keys, values));
      if(current_node.getLeft() != null){
        current_node.getLeft().setParent(current_node);
      }
      current_node.setRight(construct_and_merge(level + 1, keys, values));
      if(current_node.getRight() != null){
        current_node.getRight().setParent(current_node);
      }


      this.downHeap(current_node);

      return current_node;

    }

    public void downHeap(INode<V> node){
      //downHeap
      INode<V> cur = node;

      while(true){
        if(cur.getLeft() == null){
          break;
        }

        INode<V> min;
        if(cur.getRight() == null){
          min = cur.getLeft();
        }else{
          if(cur.getLeft().getKey() <= cur.getRight().getKey()){
            min = cur.getLeft();
          }else{
            min = cur.getRight();
          }
        }

        if(cur.getKey() > min.getKey()){
          //swap
          V tempVal = cur.getValue();
          cur.setValue(min.getValue());
          min.setValue(tempVal);

          int tempKey = cur.getKey();
          cur.setKey(min.getKey());
          min.setKey(tempKey);
          //
        }
        else if(cur.getKey() <= min.getKey()){
          break;
        }
        cur = min;
      }
      //
    }

    public void upHeap(INode<V> node){

      INode<V> cur = node;
      while(true){
        if(cur == this.root){
          break;
        }

        if(cur.getKey() < cur.getParent().getKey()){
          //swap
          V tempVal = cur.getValue();
          cur.setValue(cur.getParent().getValue());
          cur.getParent().setValue(tempVal);

          int tempKey = cur.getKey();
          cur.setKey(cur.getParent().getKey());
          cur.getParent().setKey(tempKey);
          //
        }
        else if(cur.getKey() >= cur.getParent().getKey()){
          break;
        }
        cur = cur.getParent();
      }

    }

    @Override
    public void insert(int key, V value) {
        /*
        * Function input:
        *  key: key of an entry
        *  value: value of an entry
        *
        * Job:
        *  Insert an entry with the given key and value.
        *  You can assume that each key of given entry is unique.
        */
        INode<V> newNode = new Node(key, value);
        this.size++;

        if(this.isEmpty()){
          this.root = newNode;
          this.lastNode = this.root;
          return;
        }

        //updateLast
        INode<V> cur = this.lastNode;
        if(cur == this.root){
          this.root.setLeft(newNode);
          newNode.setParent(this.root);
          cur = newNode;
        }else{
          if(this.lastNode.getParent().getLeft() == this.lastNode){
            cur.getParent().setRight(newNode);
            newNode.setParent(cur.getParent());
            cur = newNode;
          }else{
            while(true){
              if(cur == this.root){
                break;
              }
              else if(cur == cur.getParent().getLeft()){
                cur = cur.getParent().getRight();
                break;
              }
              cur = cur.getParent();
            }
            while(true){
              if(cur.getLeft() == null){
                break;
              }
              cur = cur.getLeft();
            }
            cur.setLeft(newNode);
            newNode.setParent(cur);
            cur = newNode;
          }
        }

        this.lastNode = cur;
        //

        //upHeap
        this.upHeap(this.lastNode);
        // cur = this.lastNode;
        // while(true){
        //   if(cur == this.root){
        //     break;
        //   }
        //
        //   if(cur.getKey() < cur.getParent().getKey()){
        //     //swap
        //     V tempVal = cur.getValue();
        //     cur.setValue(cur.getParent().getValue());
        //     cur.getParent().setValue(tempVal);
        //
        //     int tempKey = cur.getKey();
        //     cur.setKey(cur.getParent().getKey());
        //     cur.getParent().setKey(tempKey);
        //     //
        //   }
        //   else if(cur.getKey() >= cur.getParent().getKey()){
        //     break;
        //   }
        //   cur = cur.getParent();
        // }
        //

        return;
    }

    @Override
    public V removeMin() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Remove the entry with the smallest key in the heap and
        *  return the value of the removed entry.
        *  If the heap is empty, throw an exception.
        */
        if (this.isEmpty()){
          throw new IllegalStateException();
        }

        V valueToReturn = this.root.getValue();

        if (this.size == 1){
          this.root = null;
          this.lastNode = null;
          this.size--;
          return valueToReturn;
        }

        this.size--;
        this.root.setKey(this.lastNode.getKey());
        this.root.setValue(this.lastNode.getValue());

        //updateLast
        INode<V> cur = this.lastNode;
        if(this.lastNode.getParent().getRight() == this.lastNode){
          cur = this.lastNode.getParent().getLeft();
          this.lastNode.getParent().setRight(null);
          this.lastNode.setParent(null);
        }else{
          while(true){
            if(cur == this.root){
              break;
            }
            else if(cur == cur.getParent().getRight()){
              cur = cur.getParent().getLeft();
              break;
            }
            cur = cur.getParent();
          }
          while(true){
            if(cur.getRight() == null){
              break;
            }
            cur = cur.getRight();
          }
          this.lastNode.getParent().setLeft(null);
          this.lastNode.setParent(null);
        }
        this.lastNode = cur;
        //

        //downHeap
        this.downHeap(this.root);
        //
        // cur = this.root;
        // while(true){
        //   if(cur.getLeft() == null){
        //     break;
        //   }
        //
        //   INode<V> min;
        //   if(cur.getRight() == null){
        //     min = cur.getLeft();
        //   }else{
        //     if(cur.getLeft().getKey() <= cur.getRight().getKey()){
        //       min = cur.getLeft();
        //     }else{
        //       min = cur.getRight();
        //     }
        //   }
        //
        //   if(cur.getKey() > min.getKey()){
        //     //swap
        //     V tempVal = cur.getValue();
        //     cur.setValue(min.getValue());
        //     min.setValue(tempVal);
        //
        //     int tempKey = cur.getKey();
        //     cur.setKey(min.getKey());
        //     min.setKey(tempKey);
        //     //
        //   }
        //   else if(cur.getKey() <= min.getKey()){
        //     break;
        //   }
        //   cur = min;
        // }
        //

        return valueToReturn;
    }

    @Override
    public V min() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the value of the entry with the smallest key.
        *  If the heap is empty, throw an exception.
        */
        if (this.isEmpty()){
          throw new IllegalStateException();
        }

        return this.root.getValue();
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the number of entries in the heap.
        */

        return this.size;
    }

    @Override
    public boolean isEmpty() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return whether of not the heap is empty.
        */
        if(this.root == null){
          return true;
        }
        return false;
    }

    @Override
    public INode<V> getRoot() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the root node of the heap.
        *  If the heap is empty, throw an exception.
        */
        if (this.isEmpty()) throw new IllegalStateException();
        return this.root;
    }
}

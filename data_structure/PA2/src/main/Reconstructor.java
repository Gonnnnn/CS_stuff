

/* Reconstructor of the Tree */
public final class Reconstructor implements IReconstructor {
    /*
    * Use some variables for your implementation.
    */
    private int current;
    //the current index of the element of postOrder we are trying to find in inOrder
    private boolean not_exist;
    //inorder_searcher turns it to "true" as soon as it finds no tree exists satisfying the given arrays
    private int first_of_sub;
    //the first index of the sub array of inOrder that inOrder_searcher is going to search through
    private int last_of_sub;
    //the last index of the sub array of inOrder that inOrder_searcher is going to search through


    public Reconstructor() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */

    }

    @Override
    public ITreeNode reconstruct(Integer[] postOrder, Integer[] inOrder)
        throws IllegalStateException {
        /*
        * Function input: post-order sequence,in-order sequnce of a tree.
        *
        * Job:
        *  Reconstruct the original binary tree that gives the post-order sequence and in-order sequence.
        *  Return the root node of that tree.
        *
        *  If there is no such tree, raise an IllegalStateException.
        *  You do not have to specify its message.
        */
        if(postOrder.length != inOrder.length){
          throw new IllegalStateException();
        }

        this.setVals(postOrder, inOrder);


        ITreeNode root = inOrder_searcher(postOrder, inOrder, this.first_of_sub, this.last_of_sub);

        if(this.not_exist){
          throw new IllegalStateException();
        }

        return root;
    }

    public void setVals(Integer[] postOrder, Integer[] inOrder){

      this.current = postOrder.length - 1;
      this.not_exist = false;
      this.first_of_sub = 0;
      this.last_of_sub = inOrder.length - 1;

    }

    public ITreeNode inOrder_searcher(Integer[] postOrder, Integer[] inOrder, int first, int last){



      ITreeNode new_node = new TreeNode();
      new_node.setVal(postOrder[this.current]);



      int i;
      for(i = first; i < last + 1; i++){
        if(postOrder[this.current] == inOrder[i]){
          break;
        }
      }



      if(i == last + 1){
        this.not_exist = true;
      }



      this.current -= 1;



      if(first == last){
        return new_node;
      }



      if(first <= i && i < last){
        new_node.setRight(inOrder_searcher(postOrder, inOrder, i+1, last));
        new_node.right().setParent(new_node);
      }

      if((!this.not_exist) && (first < i && i <= last)){
        new_node.setLeft(inOrder_searcher(postOrder, inOrder, first, i-1));
        new_node.left().setParent(new_node);
      }



      return new_node;


    }

}

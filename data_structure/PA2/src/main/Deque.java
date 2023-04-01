

/* Double-ended queue */
public final class Deque<E> implements IDeque<E> {
    /*
    * Use some variables for your implementation.
    */
    private INode head;
    private INode tail;
    private int size;

    public Deque() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void insertFirst(E item) {
        /*
        * Function input:
        *  item: an item to be inserted.
        *
        * Job:
        *  Insert the given item before the first item of this deque.
        *  If this deque is empty, use this item as the first item.
        */
        INode new_node = new Node();
        new_node.setVal(item);

        if (this.isEmpty()){

          this.head = new_node;
          this.tail = new_node;

        } else {

          this.head.setPrev(new_node);
          new_node.setNext(this.head);
          this.head = new_node;

        }

        this.size += 1;

    }

    @Override
    public void insertLast(E item) {
        /*
        * Function input:
        *  item: an item to be inserted.
        *
        * Job:
        *  Insert the given item after the last item of this deque.
        *  If this deque is empty, use this item as the last item.
        */
        INode new_node = new Node();
        new_node.setVal(item);

        if (this.isEmpty()){

          this.head = new_node;
          this.tail = new_node;

        } else {

          this.tail.setNext(new_node);
          new_node.setPrev(this.tail);
          this.tail = new_node;

        }

        this.size += 1;

    }

    @Override
    public void deleteFirst()
            throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Delete the first item of the deque.
        *
        *  If there is no item, raise an IllegalStateException.
        *  You do not have to specify its message.
        */
        if (this.isEmpty()){
          throw new IllegalStateException();
        }

        if (this.size > 1){

          this.head = this.head.next();
          this.head.prev().setNext(null);
          this.head.setPrev(null);

        } else {

          this.head = null;
          this.tail = null;

        }

        this.size -= 1;

    }

    @Override
    public void deleteLast()
            throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Delete the last item of the deque.
        *
        *  If there is no item, raise an IllegalStateException.
        *  You do not have to specify its message.
        */
        if (this.isEmpty()){
          throw new IllegalStateException();
        }

        if (this.size > 1){

          this.tail = this.tail.prev();
          this.tail.next().setPrev(null);
          this.tail.setNext(null);

        } else {

          this.head = null;
          this.tail = null;

        }

        this.size -= 1;


    }

    @Override
    public E first()
            throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the first item.
        *
        *  If there is no item, raise an IllegalStateException.
        *  You do not have to specify its message.
        */
        if (this.isEmpty()){
          throw new IllegalStateException();
        }

        E item = (E) this.head.val();

        return item;
    }

    @Override
    public E last()
            throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the last item.
        *
        *  If there is no item, raise an IllegalStateException.
        *  You do not have to specify its message.
        */
        if (this.isEmpty()){
          throw new IllegalStateException();
        }

        E item = (E) this.tail.val();

        return item;
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the number of items in the deque.
        */

        return this.size;
    }

    @Override
    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}



/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class MyString implements IString{
    /*
     * Add some variables you will use.
     */
     private IListNode head;
     private IListNode tail;

    public MyString() {
        /*
         * Constructor
         * Create an empty String class
         */
         this.head = null;
         this.tail = null;

    }

    @Override
    public void append(char x) {
        /*
         * Function input:
         *  + x: A character to be appended
         *
         * Job:
         *  Insert the character to the end of the linked list.
         */

         IListNode newNode = new ListNode(x);

         if (this.size() != 0) {
           newNode.setPrev(this.tail);
           this.tail.setNext(newNode);
         }
         else {
           this.head = newNode;
         }

         this.tail = newNode;
    }

    @Override
    public void prepend(char x) {
        /*
         * Function input:
         *  + x: A character to be prepended
         *
         * Job:
         *  Insert the character to the start of the linked list.
         */
         IListNode newNode = new ListNode(x);

         if (this.size() != 0) {
           newNode.setNext(this.head);
           this.head.setPrev(newNode);
         }
         else {
           this.tail = newNode;
         }

         this.head = newNode;
    }

    @Override
    public IListNode head() {
        /*
         * Function input:
         *  None
         *
         * Job:
         *  Return the first node of the linked list. If empty, return null.
         */
      if (this.size() != 0) {
        return this.head;
      }

        return null;
    }

    @Override
    public IListNode tail() {
        /*
         * Function input:
         *  None
         *
         * Job:
         *  Return the last node of the linked list. If empty, return null.
         */
      if (this.size() != 0) {
        return this.tail;
      }

        return null;
    }

    @Override
    public int findFirst(char x) {
        /*
         * Function input:
         *  + x: A character to find
         *
         * Job:
         *  Return the smallest index which you can find x.
         *  If x is not in the string, return -1.
         */
      if (this.size() == 0) {
        return -1;
      }

      IListNode temp = this.head;

      for(int i = 0; i < this.size(); i++){
        if (temp.value() == x){
          return i;
        }
        temp = temp.next();
      }

      return -1;
    }

    @Override
    public int findLast(char x) {
        /*
         * Function input:
         *  + x: A character to find
         *
         * Job:
         *  Return the largest index which you can find x.
         *  If x is not in the string, return -1.
         */
         if (this.size() == 0) {
           return -1;
         }

         IListNode temp = this.tail;

         for(int i = 0; i < this.size(); i++){
           if (temp.value() == x){
             return this.size() - 1 - i;
           }
           temp = temp.prev();
         }

         return -1;
    }

    @Override
    public boolean lessOrEqual(IString s) {
        /*
         * Function input:
         *  + s: String to compare to.
         *
         * Job:
         *  Return if this string is less or equal to s in lexicographical order.
         */
         String s_string = new String(s.print());
         String my_string = new String(this.print());

         if (my_string.compareTo(s_string) <= 0) {
           return true;
         } else {
           return false;
         }
    }

    @Override
    public char[] print() {
        /*
         * Function input:
         *  None
         *
         * Job:
         *  Return the whole string.
         */
         char[] elements = new char[this.size()];
         IListNode temp = this.head;

         if(this.size() != 0){
           for(int i = 0; i < this.size(); i++){
             elements[i] = temp.value();
             temp = temp.next();
           }
           return elements;
         }

         return new char[]{};
    }

    @Override
    public int size() {
        /*
         * Function input:
         *  None
         *
         * Job:
         *  Return the size(length) of the string.
         */
        int size = 0;
        IListNode temp = this.head;
        if(temp != null){
          while(temp != this.tail){
            size++;
            temp = temp.next();
          }
          size++;
        }

        return size;
    }
}

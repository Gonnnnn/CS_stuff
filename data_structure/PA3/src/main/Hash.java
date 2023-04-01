/*
* Name: Kim Geon
* Student ID#:
*/

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/


public final class Hash<K> implements IHash<K> {
    /*
    * Use some variables for your implementation.
    */
    private class HashFn1 implements IHashFunction<Integer> {
        public int hash(Integer i, int length) {
            return (i % length);
        }
    }

    private class ResizeFn1 implements IResizeFunction {
        public boolean checkResize(int tablesize, int numItems) {
            if (numItems > tablesize * 0.8) {
                return true;
            }
            return false;
        }

        public int extendTable(int tablesize) {
            return tablesize * 2;
        }
    }

    private IHashFunction<K> h;
    private IResizeFunction ex;
    private List<K> table;
    private int tablesize;
    private int size;


    public Hash(int tablesize) {
        /*
         * Constructor
         * This function is an initializer for this class. You should implement your own HashFunction and ResizeFunction.
         * Input:
         *  + tablesize: the initial table size of the hash table.
        */
        this.table = new ArrayList<K>();
        for (int i = 0; i < tablesize; i++){
          table.add(null);
        }
        this.h = (IHashFunction) new HashFn1();
        this.ex = (IResizeFunction) new ResizeFn1();
        this.tablesize = tablesize;
    }

    public Hash(int tablesize, IHashFunction<K> h, IResizeFunction ex) {
        /*
         * Constructor
         * This function is an initializer for this class.
         * Input:
         *  + IHashFunction<K> h:
         *      int h.hash(K key, int tablesize): returns an integral hash value of key.
         *  + IResizeFunction ex:
         *      boolean ex.checkResize(int tablesize, int numItems): returns 'true' if the table must be extended for containing 'numItems' items. Otherwise, returns 'false'.
         *      int ex.extendTable(int tablesize): returns new tablesize for extended table.
         *  + tablesize: the initial table size of the hash table.
        */
        this.table = new ArrayList<K>();
        for (int i = 0; i < tablesize; i++){
          table.add(null);
        }
        this.h = h;
        this.ex = ex;
        this.tablesize = tablesize;
    }

    @Override
    public void put(K key) {
        /**
         * Input:
         * + key: A key to be added
         *
         * Job:
         *  Add the key into the hashtable.
         *  If the table must be extended, extend the table and retry adding the key.
         *  If the key is already in the hashtable, ignore the request.
         *  To decide whether two keys are equal,
         *  you must use _key.equals_ method.
         */
         int hashValue = this.h.hash(key, this.tablesize);
         for(int i = 0; i < this.tablesize; i++){
           if(this.table.get((hashValue + i) % this.tablesize) == null){
             this.table.set((hashValue + i) % this.tablesize, key);
             this.size++;
             break;
           }
         }



         if(this.ex.checkResize(this.tablesize, this.size)){
           int oldTableSize = this.tablesize;
           List<K> oldTable = table;
           int oldSize = this.size;

           int newTableSize = this.ex.extendTable(oldTableSize);
           List<K> newTable = new ArrayList<K>();

           for (int i = 0; i < newTableSize; i++){
             newTable.add(null);
           }

           this.tablesize = newTableSize;
           this.table = newTable;
           this.size = 0;

           for (int i = 0; i < oldTableSize; i++){
             if(oldTable.get(i) != null){
               this.put(oldTable.get(i));
             }
           }
         }

    }

    @Override
    public void remove(K key) throws IllegalStateException {
        /*
        * Input:
        *  + key: A key to be removed
        *
        * Job:
        *  Delete the key from the hash table.
        *  To decide whether two keys are equal,
        *  you must use _key.equals_ method.
        *  If the key does not exist in the table, raise an exception.
        */
        if (this.size == 0){
          throw new IllegalStateException();
        }

        int hashValue = this.h.hash(key, this.tablesize);
        int i;

        for(i = 0; i < this.tablesize; i++){
          if(key.equals(this.table.get((hashValue + i) % this.tablesize))){
            this.table.set((hashValue + i) % this.tablesize, null);
            this.size--;
            break;
          }
        }

        if (i == this.tablesize){
          throw new IllegalStateException();
        }

    }

    @Override
    public boolean exists(K key) {
        /*
        * Input:
        *  + key: A key to be checked
        *
        * Job:
        *  Return true if the key is in the table; false otherwise.
        *  To decide whether two keys are equal,
        *  you must use _key.equals_ method.
        */

        int hashValue = this.h.hash(key, this.tablesize);

        for(int i = 0; i < this.tablesize; i++){
          if(key.equals(this.table.get((hashValue + i) % this.tablesize))){
            return true;
          }
        }

        return false;
    }

    @Override
    public int size() {
        /*
        * Job:
        *  Return the number of items in the hashtable.
        */
        return this.size;
    }

    @Override
    public int tablesize() {
        /*
        * Job:
        *  Return the size of current hashtable.
        */
        return this.tablesize;
    }

    @Override
    public List<K> show() {
        /*
        * Job:
        *  Return the items in the hashtable.
        *  The list index must be the bucket index of the item.
        *  If a bucket has no item, assign null.
        *  Note that you can use ArrayList.
        */
        if(this.size == 0){
          return null;
        }
        return this.table;
    }

}

import java.util.*;

/*
* Since this file is not a part of submission,
* you can use any class/packages.
*/

public class Main
{
    public static void main(String[] args)
    {

      // IHash<Integer> h = new Hash<Integer>(5, new HashFn1(), new ResizeFn1());
      //
      // h.put(0);
      // h.put(5);
      // h.put(3);
      // System.out.println(h.tablesize());
      // System.out.println(h.size());
      // h.put(13);
      // System.out.println(h.tablesize());
      // System.out.println(h.size());
      // h.put(2);
      //
      // System.out.println(h.tablesize());
      // System.out.println(h.size());
      // System.out.println(h.show());
      // h.remove(13);
      // System.out.println(h.show());
      // System.out.println(h.exists(13));
      // System.out.println(h.exists(0));
      // h.remove(0);
      // h.remove(5);
      // h.remove(3);
      // h.remove(2);
      // System.out.println(h.size());





      IHeap<String> heap = new Heap<>();
      heap.bottomUp(new int[]{ 1 }, new String[]{ "1" });
      System.out.println(heap.size());
      System.out.println(heap.isEmpty());
      System.out.println(heap.min());
      System.out.println(heap.removeMin());
      System.out.println(heap.size());
      System.out.println(heap.isEmpty());

      //
      // heap.insert(1, "1");
      // heap.insert(6, "6");
      // heap.insert(101, "101");
      //
      // System.out.println(heap.getRoot().getKey());
      // System.out.println(heap.getRoot().getLeft().getKey());
      // System.out.println(heap.getRoot().getRight().getKey());
      // System.out.println(heap.getRoot().getLeft().getLeft().getKey());
      // System.out.println(heap.getRoot().getLeft().getRight().getKey());
      // System.out.println(heap.getRoot().getRight().getLeft().getKey());
      // System.out.println(heap.getRoot().getRight().getRight().getKey());
      // System.out.println(heap.getRoot().getLeft().getLeft().getLeft().getKey());
      // System.out.println(heap.getRoot().getLeft().getLeft().getRight().getKey());
      // System.out.println(heap.getRoot().getLeft().getRight().getLeft());





      // IHeap<String> heap = new Heap<>();
      // heap.insert(47, "47");
      // heap.bottomUp(new int[]{ 1, 2, 100, 50, 43, 2}, new String[]{ "1", "2", "100", "50", "43", "2" });
      // System.out.println(heap.isEmpty());
      // System.out.println(heap.getRoot());
      // System.out.println(heap.removeMin());
      // System.out.println(heap.min());
      // System.out.println(heap.size());



      //
      // heap.insert(100, "100");
      // System.out.println(heap.getRoot().getKey());
      // System.out.println(heap.min());
      // heap.insert(50, "50");
      // System.out.println(heap.min());
      // heap.insert(2, "2-1");
      // System.out.println(heap.min());
      // heap.insert(2, "2-2");
      // heap.insert(43, "43");
      // heap.insert(1, "1");
      // System.out.println(heap.min());
      // System.out.println(heap.removeMin());
      // //e
      // System.out.println(heap.size());
      // //5
      // System.out.println(heap.removeMin());
      // System.out.println(heap.removeMin());
      // System.out.println(heap.removeMin());
      // System.out.println(heap.removeMin());
      // System.out.println(heap.removeMin());
      // System.out.println(heap.size());








    }

}

class HashFn1 implements IHashFunction<Integer> {
    public int hash(Integer i, int length) {
        return (i % length);
    }
}

class ResizeFn1 implements IResizeFunction {
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

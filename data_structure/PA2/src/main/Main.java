import java.util.*;

/*
* Since this file is not a part of submission,
* you can use any class/packages.
*/

public class Main
{
    public static void main(String[] args)
    {
        IDeque dq = new Deque();
        dq.insertFirst(2);
        dq.insertLast(1);
        dq.insertFirst(4);
        dq.insertLast(5);
        dq.insertFirst(6);
        dq.insertLast(7);
        System.out.println("---------------------");
        System.out.print("first : ");
        System.out.println(dq.first());
        System.out.print("last : ");
        System.out.println(dq.last());
        System.out.print("size : ");
        System.out.println(dq.size());
        System.out.print("is_empty : ");
        System.out.println(dq.isEmpty());
        int size = dq.size();

        for(int i = 0; i < size; i++){
          System.out.print(dq.first());
          System.out.print(" ");
          dq.deleteFirst();
        }
        System.out.println();
        System.out.print("is_empty : ");
        System.out.println(dq.isEmpty());
        System.out.println("---------------------");


        IReconstructor tb = new Reconstructor();

        ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});

        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 15, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 15, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 15, 14, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 15, 7, 14});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 15, 14, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 15, 7, 14});

        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 7, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 7, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 7, 14, 15, 6, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 7, null, 3, 14, 6, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 7, 14, 15, 6, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 7, null, 3, 14, 6, 15});

        // ITreeNode root = tb.reconstruct(new Integer[] {4, 10, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {4, 10, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {4, 10, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {4, 10, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 5, 11, 4, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 5, 2, 4, 11, 1, 12, 6, null, 3, 14, 7, 15});

        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 3, 12, null, 6, 14, 15, 7, 2, 1}, new Integer[] {10, 4, 2, 5, 11, 1, 12, 6, null, 3, 14, 7, 15});
        // ITreeNode root = tb.reconstruct(new Integer[] {10, 4, 11, 5, 2, 12, null, 6, 14, 15, 7, 3, 1}, new Integer[] {10, 4, 3, 5, 11, 1, 12, 6, null, 2, 14, 7, 15});

        // ITreeNode root = tb.reconstruct(new Integer[] {11, 5, 2, null, 6, 14, 15, 7, 3, 1}, new Integer[] {2, 11, 5, 1, 6, null, 3, 14, 7, 15});


        System.out.println("---------------------");
        System.out.println(root.val());
        System.out.println(root.left().val());
        System.out.println(root.right().val());
        System.out.println(root.left().left().val());
        System.out.println(root.left().right().val());
        System.out.println(root.right().left().val());
        System.out.println(root.right().right().val());
        System.out.println(root.left().left().left().val());
        // System.out.println(root.left().left().right().val());
        // System.out.println(root.left().right().left().val());
        System.out.println(root.left().right().right().val());
        System.out.println(root.right().left().left().val());
        System.out.println(root.right().left().right().val());
        System.out.println(root.right().right().left().val());
        System.out.println(root.right().right().right().val());


        System.out.println("---------------------");





    }
}

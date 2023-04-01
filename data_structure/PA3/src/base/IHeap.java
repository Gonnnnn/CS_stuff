public interface IHeap<V> {
    void insert(int key, V value);
    void bottomUp(int[] keys, V[] values);
    V removeMin() throws IllegalStateException;
    V min() throws IllegalStateException;
    int size();
    boolean isEmpty();
    INode<V> getRoot() throws IllegalStateException;
}

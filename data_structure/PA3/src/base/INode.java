public interface INode<V> {
    void setParent(INode<V> node);
    void setLeft(INode<V> node);
    void setRight(INode<V> node);

    void setKey(int key);
    void setValue(V value);

    INode<V> getParent();
    INode<V> getLeft();
    INode<V> getRight();

    int getKey();
    V getValue();
}

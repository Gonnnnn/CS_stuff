public final class Node<V> implements INode<V> {
    private int key;
    private V value;

    private INode<V> parent;
    private INode<V> left;
    private INode<V> right;

    public Node(int key, V value) {
        this.key = key;
        this.value = value;

        this.parent = null;
        this.left = null;
        this.right = null;
    }


    @Override
    public void setParent(INode<V> parent) { this.parent = parent; }

    @Override
    public void setLeft(INode<V> left) { this.left = left; }

    @Override
    public void setRight(INode<V> right) { this.right = right; }

    @Override
    public void setKey(int key) { this.key = key; }

    @Override
    public void setValue(V value) { this.value = value; }

    @Override
    public INode<V> getParent() { return this.parent; }

    @Override
    public INode<V> getLeft() { return this.left; }

    @Override
    public INode<V> getRight() { return this.right; }

    @Override
    public int getKey() { return this.key; }

    @Override
    public V getValue() { return this.value; }
}

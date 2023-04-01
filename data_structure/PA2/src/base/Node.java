public final class Node<E> implements INode<E> {
    private E value;
    private INode next, prev;

    public Node() {
        this.next = null;
        this.prev = null;
        this.value = null;
    }

    @Override
    public void setNext(INode node) {
        this.next = node;
    }
    @Override
    public void setPrev(INode node) {
        this.prev = node;
    }
    @Override
    public void setVal(E value) {
        this.value = value;
    }
    @Override
    public INode next() {
        return this.next;
    }
    @Override
    public INode prev() {
        return this.prev;
    }
    @Override
    public E val() {
        return this.value;
    }
}

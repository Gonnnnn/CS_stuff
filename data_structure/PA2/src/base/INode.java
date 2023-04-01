public interface INode<E> {
    void setNext(INode node);
    void setPrev(INode node);
    void setVal(E value);
    INode next();
    INode prev();
    E val();
}

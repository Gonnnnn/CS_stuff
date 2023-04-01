public interface IListNode {
    void setNext(IListNode n);
    void setPrev(IListNode n);
    
    IListNode next();
    IListNode prev();

    char value();
}

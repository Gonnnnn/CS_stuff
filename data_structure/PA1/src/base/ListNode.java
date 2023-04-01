public class ListNode implements IListNode {
    private IListNode next, prev;
    private char value;

    public ListNode() {
        this.next = null;
        this.prev = null;
        this.value = '\0';
    }

    public ListNode(char value) {
        this.next = null;
        this.prev = null;
        this.value = value;
    }

    public ListNode(char value, IListNode next, IListNode prev) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    public void setNext(IListNode n) {
        this.next = n;
    }
    public void setPrev(IListNode n) {
        this.prev = n;
    }
    
    public IListNode next() {
        return this.next;
    }

    public IListNode prev() {
        return this.prev;
    }

    public char value() {
        return this.value;
    }
}

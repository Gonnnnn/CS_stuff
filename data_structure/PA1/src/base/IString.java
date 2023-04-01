public interface IString {
    void append(char x);
    void prepend(char x);

    IListNode head();
    IListNode tail();

    int findFirst(char x);
    int findLast(char x);

    boolean lessOrEqual(IString s);
    char[] print();
    int size();
}

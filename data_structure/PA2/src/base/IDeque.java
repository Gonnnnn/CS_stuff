public interface IDeque<E> {

    void insertFirst(E item);
    void insertLast(E item);
    void deleteFirst() throws IllegalStateException;
    void deleteLast() throws IllegalStateException;

    E first() throws IllegalStateException;
    E last() throws IllegalStateException;

    int size();
    boolean isEmpty();

}
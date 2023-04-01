import java.util.List;

public interface IHash<K> {
    void put(K key);

    void remove(K key) throws IllegalStateException;

    boolean exists(K key);

    int size();

    int tablesize();

    List<K> show();
}

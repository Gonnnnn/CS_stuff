public interface IHashFunction<K> {
    int hash(K key, int tablesize);
}

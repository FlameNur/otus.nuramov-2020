/**
 *
 * @param <K>
 * @param <V>
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    CacheElement<V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
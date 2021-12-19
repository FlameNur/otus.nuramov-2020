

public interface CacheEngine<K, V> {

    void put(CacheElement<V> element);

    CacheElement<V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
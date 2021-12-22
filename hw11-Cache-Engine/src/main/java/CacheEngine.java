/**
 * interface CacheEngine определяет методы работы с кэшом
 * @param <K> - ключ в кэше
 * @param <V> - значение в кэше
 */
public interface CacheEngine<K, V> {

    /**
     * Метод put позволяет положить элементы в кэш
     * @param key - ключ в кэше
     * @param value - значение в кэше
     */
    void put(K key, V value);

    /**
     * Метод get позволяет получить элемент из кэша
     * @param key - ключ кэша, по которому получаем нужный элемент
     * @return - элемент кэша
     */
    CacheElement<V> get(K key);

    /**
     * Метод getHitCount позволяет получить количество удачных запросов в кэш
     * @return - количество удачных запросов в кэш
     */
    int getHitCount();

    /**
     * Метод getMissCount позволяет получить количество неудачных запросов в кэш
     * @return - количество неудачных запросов в кэш
     */
    int getMissCount();

    /**
     * Метод dispose используется при работе с Timer (отключает его по окончанию работы)
     */
    void dispose();
}
package com.nuramov.hw11_CacheEngine.cache_engine;

import java.util.Optional;

/**
 * interface com.nuramov.hw11_CacheEngine.CacheEngine.CacheEngine определяет методы работы с кэшом
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
    Optional<V> get(K key);

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
     * Метод dispose позволяет удалить все элементы из кэша
     */
    void dispose();

    /**
     * Метод timerStop отключает Timer по окончанию работы
     */
    void timerStop();
}
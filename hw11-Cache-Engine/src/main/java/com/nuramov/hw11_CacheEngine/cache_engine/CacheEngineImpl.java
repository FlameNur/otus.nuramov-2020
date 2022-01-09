package com.nuramov.hw11_CacheEngine.cache_engine;

import com.nuramov.hw11_CacheEngine.cache_element.CacheElement;

import java.util.*;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    // Запас по времени
    private static final int TIME_THRESHOLD_MS = 5;

    // Максимальное количество элементов в кэше
    private final int maxElements;

    // Время жизни объекта в кэше/сколько объекту позволяется жить в кэше до его удаления оттуда
    private final long lifeTimeMs;

    // Сколько объект может простаивать с момента последнего запроса/доступа
    private final long idleTimeMs;

    // Кэш
    private final Map<K, CacheElement<V>> elements = new LinkedHashMap<>();

    // Таймер для планирование задач по времени
    private final Timer timer = new Timer();

    // Количество удачных запросов в кэш
    private int hit = 0;

    // Количетво неудачных запросов в кэш
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
    }

    @Override
    public void put(K key, V value) {
        // Перед добавлением элемента в кэш, проводим проверку размера кэша,
        // если он заполнен, то удаляем первый добавленный элемент/самый старый элемент
        // FIFO eviction policy
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        // Создаем обертку SoftReference вокруг объекта value
        CacheElement<V> cacheElement = new CacheElement<>(value);

        // Кладем элементы в Map
        elements.put(key, cacheElement);

        if (lifeTimeMs != 0) {
            TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
            timer.schedule(lifeTimerTask, lifeTimeMs);
        }
        if (idleTimeMs != 0) {
            TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
            timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
        }
    }

    @Override
    public V get(K key) {
        CacheElement<V> element = elements.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
            return element.getElement();
        } else {
            miss++;
        }
        return null;

        /*Optional<CacheElement<V>> optionalElement = Optional.ofNullable(elements.get(key));
        CacheElement<V> element;

        if(optionalElement.isPresent()) {
            hit++;
            element = optionalElement.get();
            element.setAccessed();
            return element.getElement();
        } else {
            miss++;
            return element = optionalElement.orElse(new CacheElement<V>());
        }*/
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        elements.clear();
        //timer.cancel();
    }

    // При работе с lifeTime и idleTime можно планировать запуск задания на определённое время в будущем (с таймером)
    // Удаляем элемент в кэше, если =null или вышло требуемое время
    private TimerTask getTimerTask(final K key, Function<CacheElement<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<V> element = elements.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    // Задаем запас по времени (в мс) и сравниваем
    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
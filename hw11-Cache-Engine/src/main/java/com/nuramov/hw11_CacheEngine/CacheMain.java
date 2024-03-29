package com.nuramov.hw11_CacheEngine;

import com.nuramov.hw11_CacheEngine.cache_engine.CacheEngine;
import com.nuramov.hw11_CacheEngine.cache_engine.CacheEngineImpl;

public class CacheMain {
    public static void main(String[] args) throws InterruptedException {
        new CacheMain().eternalCacheExample();
        new CacheMain().lifeCacheExample();
        new CacheMain().idleCacheExample();
    }

    // Метод eternalCacheExample определяет работу с кэшом по максимальному количеству элементов в кэше
    private void eternalCacheExample() {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 0);

        for (int i = 0; i < 10; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < 10; i++) {
            String element;
            if(cache.get(i).isPresent()) {
                element = cache.get(i).get();
            } else {
                element = "null";
            }
            System.out.println("String for " + i + ": " + element);
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.timerStop();
    }

    // Метод lifeCacheExample определяет работу с кэшом по lifeTime/удаляет элементы из кэша по истечению lifeTime
    private void lifeCacheExample() throws InterruptedException {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 1000, 0);

        for (int i = 0; i < size; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < size; i++) {
            String element;
            if(cache.get(i).isPresent()) {
                element = cache.get(i).get();
            } else {
                element = "null";
            }
            System.out.println("String for " + i + ": " + element);
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        // lifeTime = 1000, ждем 1000 мс и все элементы в кэше =null
        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            String element;
            if(cache.get(i).isPresent()) {
                element = cache.get(i).get();
            } else {
                element = "null";
            }
            System.out.println("String for " + i + ": " + element);
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.timerStop();
    }

    private void idleCacheExample() throws InterruptedException {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 3000);

        for (int i = 0; i < size; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < size; i++) {
            String element;
            if(cache.get(i).isPresent()) {
                element = cache.get(i).get();
            } else {
                element = "null";
            }
            System.out.println("String for " + i + ": " + element);
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        for (int i = 0; i < size; i++) {
            String element;
            if(cache.get(i).isPresent()) {
                element = cache.get(i).get();
            } else {
                element = "null";
            }

            // idleTime = 3000, ждем по 3000 мс, по истечению idleTime элементы в кэше будут удалены
            Thread.sleep(3000);

            System.out.println("String for " + i + ": " + element);
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.timerStop();
    }
}

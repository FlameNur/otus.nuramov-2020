public class CacheMain {
    public static void main(String[] args) throws InterruptedException {
        //new CacheMain().eternalCacheExample();
        new CacheMain().lifeCacheExample();
    }

    // Метод eternalCacheExample определяет работу с кэшом по максимальному количеству элементов в кэше
    private void eternalCacheExample() {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 0, true);

        for (int i = 0; i < 10; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < 10; i++) {
            CacheElement<String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getElement() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    // Метод lifeCacheExample определяет работу с кэшом по lifeTime/удаляет элементы из кэша по истечению lifeTime
    private void lifeCacheExample() throws InterruptedException {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < size; i++) {
            CacheElement<String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getElement() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        // lifeTime = 1000, ждем 1000 мс и все элементы в кэше =null
        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            CacheElement<String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getElement() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }
}

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    // ???
    private static final int TIME_THRESHOLD_MS = 5;

    // Максимальное количество элементов в кэше
    private final int maxElements;

    // Время жизни объекта в кэше/сколько объекту позволяется жить в кэше до его удаления оттуда
    private final long lifeTimeMs;

    // Сколько объект может простаивать с момента последнего запроса/доступа
    private final long idleTimeMs;

    // Если lifeTime и idleTime =0, то все элементы живут в кэше вечно и будут удаляться по максимальному размеру
    private final boolean isEternal;

    private final Map<K, CacheElement<V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    // Количество удачных запросов в кэш
    private int hit = 0;

    // Количетво неудачных запросов в кэш
    private int miss = 0;

    CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    // кладем элемент в кэш
    public void put(K key, V value) {
        // Перед добавлением элемента в кэш, проводим проверку размера кэша,
        // если он заполнен, то удаляем первый добавленный элемент/самый старый элемент
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        CacheElement<V> cacheElement = new CacheElement<>(value);

        // дальше пока хз что там...
        elements.put(key, cacheElement);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    public CacheElement<V> get(K key) {
        CacheElement<V> element = elements.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    // Задаем максимальное время, которое элемент в кэше должен жить
    // Работает с lifeTime и idleTime
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


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
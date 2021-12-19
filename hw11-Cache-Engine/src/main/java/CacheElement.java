import java.lang.ref.SoftReference;

public class CacheElement<V> {
    private final SoftReference<V> softReferenceElement;
    private final long creationTime;
    private long lastAccessTime;


    public CacheElement(V dbElement) {
        this.softReferenceElement = new SoftReference<>(dbElement);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    V getElement() {
        return softReferenceElement.get();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    long getCreationTime() {
        return creationTime;
    }

    long getLastAccessTime() {
        return lastAccessTime;
    }

    void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}

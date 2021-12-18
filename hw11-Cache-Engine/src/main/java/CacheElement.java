import java.lang.ref.SoftReference;

public class CacheElement<T> {
    private final SoftReference<T> softReferenceElement;
    private final long creationTime;
    private long lastAccessTime;


    public CacheElement(T dbElement) {
        this.softReferenceElement = new SoftReference<>(dbElement);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    T getElement() {
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

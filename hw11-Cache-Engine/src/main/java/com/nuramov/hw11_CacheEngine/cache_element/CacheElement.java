package com.nuramov.hw11_CacheEngine.cache_element;

import java.lang.ref.SoftReference;

/**
 * class com.nuramov.hw11_CacheEngine.CacheElement.CacheElement создет обертку SoftReference вокруг полученного объекта из БД
 * GC удаляет все объекты SoftReference при переполненности памяти,
 * т.е. все объекты SoftReference будут удалены до ошибки OutOfMemory
 * @param <T> - класс объекта, который мы получили из БД
 */
public class CacheElement<T> {
    private final SoftReference<T> softReferenceElement;
    private final long creationTime;
    private long lastAccessTime;


    public CacheElement(T dbElement) {
        this.softReferenceElement = new SoftReference<>(dbElement);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    public T getElement() {
        return softReferenceElement.get();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}

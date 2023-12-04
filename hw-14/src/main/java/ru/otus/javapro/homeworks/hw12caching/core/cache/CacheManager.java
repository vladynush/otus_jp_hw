package ru.otus.javapro.homeworks.hw12caching.core.cache;

public interface CacheManager<K, V> {

    void put(K key, V value);

    boolean exist(K key);

    V get(K key);

    void invalidate();
}

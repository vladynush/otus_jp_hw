package ru.otus.javapro.homeworks.hw12caching.core.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoftReferenceCacheManager<K, V> implements CacheManager<K, V> {

    private final Map<K, SoftReference<V>> cacheMap = new HashMap<>();

    @Override
    public void put(K key, V value) {
        cacheMap.put(key, new SoftReference<>(value));
    }

    @Override
    public boolean exist(K key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public V get(K key) {
        SoftReference<V> softReference = cacheMap.get(key);
        if (!Objects.isNull(softReference)) {
            V value = softReference.get();
            if (!Objects.isNull(value)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public void invalidate() {
        cacheMap.clear();
    }
}

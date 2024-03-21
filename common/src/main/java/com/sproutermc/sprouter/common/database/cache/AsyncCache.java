package com.sproutermc.sprouter.common.database.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AsyncCache<K, V> {

    protected final AsyncLoadingCache<K, V> cache;

    public AsyncCache() {
        this(5);
    }

    public AsyncCache(int expirationInMinutes) {
        cache = Caffeine.newBuilder()
                .expireAfterAccess(expirationInMinutes, TimeUnit.MINUTES)
                .buildAsync((key, executor) -> createCacheEntry(key));
    }

    public CompletableFuture<V> get(K key) {
        return cache.get(key);
    }

    public V awaitGet(K key) {
        // synchronous calls are less expensive, lets try grabbing it there first
        V cachedValue = cache.synchronous().getIfPresent(key);
        if (cachedValue != null) {
            return cachedValue;
        }
        try {
            return get(key).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to await cache result", e);
        }
    }

    public void remove(K key) {
        cache.synchronous().invalidate(key);
    }

    public void removeAll() {
        cache.synchronous().invalidateAll();
    }

    abstract CompletableFuture<V> createCacheEntry(K key);

}

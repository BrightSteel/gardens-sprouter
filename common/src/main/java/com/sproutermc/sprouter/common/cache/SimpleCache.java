package com.sproutermc.sprouter.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.concurrent.TimeUnit;

// does not reset expiration tick on read
public class SimpleCache<K, V> {

    private final Cache<K, V> cache;

    public SimpleCache(int expirationInMinutes) {
        long expirationInNanos = TimeUnit.MINUTES.toNanos(expirationInMinutes);
        cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<>() {
                    @Override
                    public long expireAfterCreate(Object key, Object value, long currentTime) {
                        return expirationInNanos;
                    }

                    @Override
                    public long expireAfterUpdate(Object key, Object value, long currentTime, @NonNegative long currentDuration) {
                        return expirationInNanos;
                    }

                    @Override
                    public long expireAfterRead(Object key, Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration; // does not affect tick
                    }
                })
                .build();
    }

    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public V remove(K key) {
        V valueRemoved = get(key);
        cache.invalidate(key);
        return valueRemoved;
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }
}

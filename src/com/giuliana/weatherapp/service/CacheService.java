package com.giuliana.weatherapp.service;

import java.util.HashMap;
import java.util.Map;

public class CacheService {

    private final Map<String, Object> cache = new HashMap<>();

    public void put(String key, Object value) {
        cache.put(key.toLowerCase(), value);
    }

    public Object get(String key) {
        return cache.get(key.toLowerCase());
    }

    public boolean contains(String key) {
        return cache.containsKey(key.toLowerCase());
    }
}

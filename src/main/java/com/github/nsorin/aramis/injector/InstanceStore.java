package com.github.nsorin.aramis.injector;

import java.util.HashMap;
import java.util.Map;

public class InstanceStore {

    Map<Class<?>, Object> instances = new HashMap<>();

    <T, U extends T> void register(Class<T> type, U instance) {
        instances.put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public <T, U extends T> U getImplementationInstance(Class<T> type) {
        return (U) instances.get(type);
    }

    boolean isRegistered(Class<?> type) {
        return instances.containsKey(type);
    }

    boolean isInitialized(Class<?> type) {
        return instances.get(type) != null;
    }
}

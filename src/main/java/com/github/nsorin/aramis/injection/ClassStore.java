package com.github.nsorin.aramis.injection;

import java.util.HashMap;
import java.util.Map;

class ClassStore {
    private final Map<Class<?>, Class<?>> classes = new HashMap<>();

    <T, U extends T> void register(Class<T> serviceInterface, Class<U> serviceImplementation) {
        classes.put(serviceInterface, serviceImplementation);
    }

    @SuppressWarnings("unchecked")
    <T, U extends T> Class<U> getImplementationClass(Class<T> type) {
        return (Class<U>) classes.get(type);
    }
}

package com.github.nsorin.aramis.injection;

import java.lang.reflect.Constructor;

public class ConstuctorFinder {

    @SuppressWarnings("unchecked")
    <T> Constructor<T> findInjectableConstructor(Class<T> clientType) throws NoSuchMethodException {
        for (Constructor<?> constructor : clientType.getConstructors()) {
            if (constructor.isAnnotationPresent(Injected.class)) {
                return (Constructor<T>) constructor;
            }
        }
        return clientType.getConstructor();
    }
}

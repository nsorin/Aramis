package com.github.nsorin.aramis.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class InjectableFinder {

    @SuppressWarnings("unchecked")
    <T> Constructor<T> findInjectableConstructor(Class<T> clientType) throws NoSuchMethodException {
        for (Constructor<?> constructor : clientType.getConstructors()) {
            if (constructor.isAnnotationPresent(Injectable.class)) {
                return (Constructor<T>) constructor;
            }
        }
        return clientType.getConstructor();
    }

    <T> List<Field> findInjectableFields(T client) {
        return Arrays.stream(client.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Injectable.class))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }

    <T> List<Method> findInjectableSetters(T client) {
        return Arrays.stream(client.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Injectable.class))
                .collect(Collectors.toList());
    }
}

package com.github.nsorin.aramis.injection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class FieldFinder {

    private final ClassStore store;

    FieldFinder(ClassStore store) {
        this.store = store;
    }

    <T> List<Field> findInjectableFields(T client) {
        return Arrays.stream(client.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Injected.class))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }

    void inject(Object client) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clientClass = client.getClass();
        for (Field field : clientClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Injected.class)) {
                setField(client, field);
            }
        }
    }

    private void setField(Object client, Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        field.setAccessible(true);
        Class<?> type = field.getType();
        field.set(client, type.cast(store.resolve(type)));
    }
}

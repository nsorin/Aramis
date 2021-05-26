package com.github.nsorin.aramis.injection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

class FieldInjector {

    private final ClassStore store;

    FieldInjector(ClassStore store) {
        this.store = store;
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

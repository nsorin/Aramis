package com.github.nsorin.textn.injection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

class FieldInjector {

    private final ClassStore store;

    FieldInjector(ClassStore store) {
        this.store = store;
    }

    void inject(Object client) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clientClass = client.getClass();
        for (Field field : clientClass.getFields()) {
            if (field.isAnnotationPresent(Injected.class)) {
                Class<?> type = field.getType();
                field.set(client, type.cast(store.resolve(type)));
            }
        }
    }
}

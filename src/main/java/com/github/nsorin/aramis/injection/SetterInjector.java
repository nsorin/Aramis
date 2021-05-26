package com.github.nsorin.aramis.injection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SetterInjector {

    private final ClassStore store;

    SetterInjector(ClassStore store) {
        this.store = store;
    }

    void inject(Object client) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clientClass = client.getClass();
        for (Method method : clientClass.getMethods()) {
            if (method.isAnnotationPresent(Injected.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Class<?> type = parameterTypes[0];
                    method.invoke(client, type.cast(store.resolve(type)));
                }
            }
        }
    }
}

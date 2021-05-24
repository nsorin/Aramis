package com.github.nsorin.textn.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConstuctorInjector {
    private final ClassStore store;

    ConstuctorInjector(ClassStore store) {
        this.store = store;
    }

    <T> T createAndInject(Class<T> clientType) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Constructor<?> constructor : clientType.getConstructors()) {
            if (constructor.isAnnotationPresent(Injected.class)) {
                return clientType.cast(constructClientWithDependencies(constructor));
            }
        }
        return clientType.getConstructor().newInstance();
    }

    @SuppressWarnings("unchecked")
    private <T> T constructClientWithDependencies(Constructor<?> constructor) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Object> parameters = new ArrayList<>();
        for (Class<?> type : constructor.getParameterTypes()) {
            parameters.add(type.cast(store.resolve(type)));
        }
        return (T) constructor.newInstance(parameters.toArray());
    }
}

package com.github.nsorin.aramis.injection;

import java.lang.reflect.InvocationTargetException;

class DependencyInjector {

    private final ClassStore store;

    private final ConstuctorInjector constuctorInjector;

    private final FieldInjector fieldInjector;

    private final SetterInjector setterInjector;

    DependencyInjector(ClassStore store) {
        this.store = store;
        constuctorInjector = new ConstuctorInjector(store);
        fieldInjector = new FieldInjector(store);
        setterInjector = new SetterInjector(store);
    }

    ClassStore getStore() {
        return store;
    }

    <T> T createWithDependencies(Class<T> type) {
        try {
            T client = constuctorInjector.createAndInject(type);
            injectDependencies(client);
            return client;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new DependencyInjectionException(type);
        }
    }

    private void injectDependencies(Object client) {
        try {
            fieldInjector.inject(client);
            setterInjector.inject(client);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new DependencyInjectionException(client.getClass());
        }
    }
}

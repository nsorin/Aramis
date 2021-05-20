package com.github.nsorin.textn.injection;

import java.lang.reflect.InvocationTargetException;

class DependencyInjector {

    private static DependencyInjector injector;

    private final ClassStore store;

    private final FieldInjector fieldInjector;

    private final SetterInjector setterInjector;

    static DependencyInjector getInstance() {
        if (injector == null) {
            injector = buildInjector();
        }
        return injector;
    }

    private static DependencyInjector buildInjector() {
        return new DependencyInjector(new ClassStore());
    }

    DependencyInjector(ClassStore store) {
        this.store = store;
        this.fieldInjector = new FieldInjector(store);
        this.setterInjector = new SetterInjector(store);
    }

    ClassStore getStore() {
        return store;
    }

    void injectDependencies(Object client) {
        try {
            this.fieldInjector.inject(client);
            this.setterInjector.inject(client);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new DependencyInjectionException();
        }
    }
}

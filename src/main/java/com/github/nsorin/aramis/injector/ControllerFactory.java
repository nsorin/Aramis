package com.github.nsorin.aramis.injector;

import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private final DependencyInjector dependencyInjector;

    ControllerFactory(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
    }

    @Override
    public Object call(Class<?> aClass) {
        return dependencyInjector.resolve(aClass);
    }
}

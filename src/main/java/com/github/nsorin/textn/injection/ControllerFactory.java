package com.github.nsorin.textn.injection;

import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private final DependencyInjector dependencyInjector;

    ControllerFactory(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
    }

    @Override
    public Object call(Class<?> aClass) {
        try {
            Object controller = aClass.getDeclaredConstructor().newInstance();
            dependencyInjector.injectDependencies(controller);
            return controller;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Could not resolve controller " + aClass);
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}

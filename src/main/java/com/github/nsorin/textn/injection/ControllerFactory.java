package com.github.nsorin.textn.injection;

import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class ControllerFactory implements Callback<Class<?>, Object> {

    @Override
    public Object call(Class<?> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Could not resolve controller " + aClass);
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}

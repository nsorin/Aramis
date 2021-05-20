package com.github.nsorin.textn.injection;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ClassStore {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Map<Class<?>, Class<?>> classes = new HashMap<>();

    public <T, U extends T> void register(Class<T> serviceInterface, Class<U> serviceImplementation) {
        classes.put(serviceInterface, serviceImplementation);
    }

    public <T> void registerInstance(Class<T> testServiceClass, T registered) {
        instances.put(testServiceClass, registered);
    }

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> testServiceClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (instances.containsKey(testServiceClass)) {
            return (T) instances.get(testServiceClass);
        } else if (classes.containsKey(testServiceClass)) {
            return (T) classes.get(testServiceClass).getDeclaredConstructor().newInstance();
        } else if(!testServiceClass.isInterface()) {
            return testServiceClass.getDeclaredConstructor().newInstance();
        }
        throw new InterfaceNotRegisteredException();
    }
}

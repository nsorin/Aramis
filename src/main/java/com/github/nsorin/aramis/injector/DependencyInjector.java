package com.github.nsorin.aramis.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class DependencyInjector {

    private final static int MAX_DEPENDENCY_DEPTH = 10;

    private final ClassStore classStore;

    private final InstanceStore instanceStore;

    private final InjectableFinder injectableFinder = new InjectableFinder();

    DependencyInjector(ClassStore classStore, InstanceStore instanceStore) {
        this.classStore = classStore;
        this.instanceStore = instanceStore;
    }

    <T, U extends T> void register(Class<T> serviceInterface, Class<U> serviceImplementation) {
        classStore.register(serviceInterface, serviceImplementation);
    }

    <T, U extends T> void registerSingleton(Class<T> serviceInterface, Class<U> serviceImplementation) {
        classStore.register(serviceInterface, serviceImplementation);
        instanceStore.register(serviceInterface, null);
    }

    @SuppressWarnings("unchecked")
    <T, U extends T> void registerInstance(Class<T> serviceInterface, U serviceInstance) {
        classStore.register(serviceInterface, (Class<U>) serviceInstance.getClass());
        instanceStore.register(serviceInterface, serviceInstance);
    }

    <T> T resolve(Class<T> type) {
        return resolve(type, 0);
    }

    @SuppressWarnings("unchecked")
    private <T, U extends T> U resolve(Class<T> type, int depth) {
        if (depth >= MAX_DEPENDENCY_DEPTH) {
            throw new MaxDependencyDepthReachedException(type, MAX_DEPENDENCY_DEPTH);
        }

        if (instanceStore.isInitialized(type)) {
            return instanceStore.getImplementationInstance(type);
        }

        try {
            Class<U> implementation = depth == 0 ? (Class<U>) type : classStore.getImplementationClass(type);
            U result = callConstructor(injectableFinder.findInjectableConstructor(implementation), depth);
            injectFields(injectableFinder.findInjectableFields(result), result, depth);
            injectSetters(injectableFinder.findInjectableSetters(result), result, depth);
            if (instanceStore.isRegistered(type)) {
                instanceStore.register(type, result);
            }
            return result;
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new DependencyInjectionException(type);
        }
    }

    private <T> T callConstructor(Constructor<T> constructor, int depth) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> parameters = new ArrayList<>();
        for (Class<?> type : constructor.getParameterTypes()) {
            parameters.add(type.cast(resolve(type, depth + 1)));
        }
        return constructor.newInstance(parameters.toArray());
    }

    private <T> void injectFields(List<Field> fields, T client, int depth) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Injectable.class)) {
                Class<?> type = field.getType();
                field.set(client, type.cast(resolve(type, depth + 1)));
            }
        }
    }

    private <T> void injectSetters(List<Method> setters, T client, int depth) throws InvocationTargetException, IllegalAccessException {
        for (Method setter : setters) {
            List<Object> parameters = new ArrayList<>();
            for (Class<?> type : setter.getParameterTypes()) {
                parameters.add(type.cast(resolve(type, depth + 1)));
            }
            setter.invoke(client, parameters.toArray());
        }
    }
}

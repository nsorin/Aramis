package com.github.nsorin.aramis.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class DependencyInjector {

    private final static int MAX_DEPENDENCY_DEPTH = 10;

    private final ClassStore store;

    private final InjectableFinder injectableFinder = new InjectableFinder();

    DependencyInjector(ClassStore store) {
        this.store = store;
    }

    ClassStore getStore() {
        return store;
    }

    <T> T resolve(Class<T> type) {
        return resolve(type, 0);
    }

    @SuppressWarnings("unchecked")
    private <T, U extends T> U resolve(Class<T> type, int depth) {
        if (depth >= MAX_DEPENDENCY_DEPTH) {
            throw new MaxDependencyDepthReachedException(type, MAX_DEPENDENCY_DEPTH);
        }

        try {
            Class<U> implementation = depth == 0 ? (Class<U>) type : store.getImplementationClass(type);
            U result = callConstructor(injectableFinder.findInjectableConstructor(implementation), depth);
            injectFields(injectableFinder.findInjectableFields(result), result, depth);
            injectSetters(injectableFinder.findInjectableSetters(result), result, depth);
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

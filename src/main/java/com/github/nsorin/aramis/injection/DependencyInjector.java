package com.github.nsorin.aramis.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class DependencyInjector {

    private final ClassStore store;

    private final ConstuctorFinder constuctorFinder;

    private final FieldFinder fieldFinder;

    private final SetterFinder setterFinder;

    DependencyInjector(ClassStore store) {
        this.store = store;
        constuctorFinder = new ConstuctorFinder();
        fieldFinder = new FieldFinder(store);
        setterFinder = new SetterFinder();
    }

    ClassStore getStore() {
        return store;
    }

    <T> T resolve(Class<T> type) {
        return resolve(type, true);
    }

    @SuppressWarnings("unchecked")
    private <T, U extends T> U resolve(Class<T> type, boolean isImplementation) {
        try {
            Class<U> implementation = isImplementation ? (Class<U>) type : store.getImplementationClass(type);
            U result = callConstructor(constuctorFinder.findInjectableConstructor(implementation));
            injectFields(fieldFinder.findInjectableFields(result), result);
            injectSetters(setterFinder.findInjectableSetters(result), result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DependencyInjectionException(type);
        }
    }

    private <T> T callConstructor(Constructor<T> constructor) throws Exception {
        List<Object> parameters = new ArrayList<>();
        for (Class<?> type : constructor.getParameterTypes()) {
            parameters.add(type.cast(resolve(type, false)));
        }
        return constructor.newInstance(parameters.toArray());
    }

    private <T> void injectFields(List<Field> fields, T client) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Injected.class)) {
                Class<?> type = field.getType();
                field.set(client, type.cast(resolve(type, false)));
            }
        }
    }

    private <T> void injectSetters(List<Method> setters, T client) throws InvocationTargetException, IllegalAccessException {
        for (Method setter : setters) {
            List<Object> parameters = new ArrayList<>();
            for (Class<?> type : setter.getParameterTypes()) {
                parameters.add(type.cast(resolve(type, false)));
            }
            setter.invoke(client, parameters.toArray());
        }
    }
}

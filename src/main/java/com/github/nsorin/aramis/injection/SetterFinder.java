package com.github.nsorin.aramis.injection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class SetterFinder {

    <T> List<Method> findInjectableSetters(T client) {
        return Arrays.stream(client.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Injected.class))
                .collect(Collectors.toList());
    }
}

package com.github.nsorin.aramis.observer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class EventAutoSubscriber implements UnaryOperator<Object> {
    private final EventObserver eventObserver;

    public EventAutoSubscriber(EventObserver eventObserver) {
        this.eventObserver = eventObserver;
    }

    @Override
    public Object apply(Object object) {
        Class<?> type = object.getClass();
        List<Method> listenerMethods = Arrays.stream(type.getMethods())
                .filter(method -> method.isAnnotationPresent(OnEvent.class))
                .collect(Collectors.toList());

        for (Method method : listenerMethods) {
            if (method.getParameterTypes().length == 1) {
                eventObserver.subscribe(method.getParameterTypes()[0], object, method);
            } else {
                throw new InvalidEventListenerException(type, method);
            }
        }
        return object;
    }
}

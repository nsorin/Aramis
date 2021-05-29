package com.github.nsorin.aramis.observer;

import java.lang.reflect.Method;

public class InvalidEventListenerException extends RuntimeException {
    private final Class<?> type;
    private final Method method;

    public InvalidEventListenerException(Class<?> type, Method method) {
        this.type = type;
        this.method = method;
    }

    @Override
    public String getMessage() {
        return "Method " + method.getName() + " in " + type + " must have exactly one parameter to be registered as an Event Listener";
    }
}

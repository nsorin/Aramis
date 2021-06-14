package com.github.nsorin.aramis.observer;

public class ListenerNotCallableException extends RuntimeException {
    private final Class<?> eventClass;
    private final EventListener eventListener;

    public ListenerNotCallableException(Class<?> eventClass, EventListener eventListener) {
        this.eventClass = eventClass;
        this.eventListener = eventListener;
    }

    @Override
    public String getMessage() {
        return "Could not call method " + eventListener.method().getName()
                + " in " + eventListener.object().getClass()
                + " with event of type " + eventClass;
    }
}

package com.github.nsorin.aramis.observer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventObserver implements EventObserverInterface {

    private static EventObserver observer;

    private final Map<Class<?>, List<EventListener>> eventListeners = new HashMap<>();

    private EventObserver() {
    }

    public static EventObserver getObserver() {
        if (observer == null) {
            observer = new EventObserver();
        }
        return observer;
    }

    public static void resetObserver() {
        observer = null;
    }

    <T> void subscribe(Class<?> eventClass, T object, Method method) {
        if (!eventListeners.containsKey(eventClass)) {
            eventListeners.put(eventClass, new ArrayList<>());
        }
        eventListeners.get(eventClass).add(new EventListener(object, method));
    }

    <T> void unsubscribe(Class<?> eventClass, T object, Method method) {
        if (eventListeners.containsKey(eventClass)) {
            eventListeners.get(eventClass).remove(new EventListener(object, method));
        }
    }

    public <T> void emit(T event) {
        Class<?> eventClass = event.getClass();
        if (eventListeners.containsKey(eventClass)) {
            for (EventListener eventListener : eventListeners.get(eventClass)) {
                try {
                    eventListener.call(event);
                } catch (Exception e) {
                    throw new ListenerNotCallableException(eventClass, eventListener);
                }
            }
        }
    }
}

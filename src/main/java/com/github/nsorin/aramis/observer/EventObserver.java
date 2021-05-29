package com.github.nsorin.aramis.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventObserver {

    private final Map<Class<?>, List<ListensTo<?>>> listeners = new HashMap<>();

    public <T> void subscribe(ListensTo<T> listener, Class<T> eventClass) {
        if (!listeners.containsKey(eventClass)) {
            listeners.put(eventClass, new ArrayList<>());
        }
        listeners.get(eventClass).add(listener);
    }

    public <T> void unsubscribe(ListensTo<T> listener, Class<T> eventClass) {
        if (listeners.containsKey(eventClass)) {
            listeners.get(eventClass).remove(listener);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void emit(T hello) {
        for (ListensTo<?> listener : listeners.get(hello.getClass())) {
            ((ListensTo<T>) listener).on(hello);
        }
    }
}

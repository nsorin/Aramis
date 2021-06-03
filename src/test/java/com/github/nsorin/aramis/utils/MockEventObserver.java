package com.github.nsorin.aramis.utils;

import com.github.nsorin.aramis.observer.EventObserverInterface;

import java.util.ArrayList;
import java.util.List;

public class MockEventObserver implements EventObserverInterface {

    private final List<Object> events = new ArrayList<>();

    @Override
    public <T> void emit(T event) {
        this.events.add(event);
    }

    public Object getLastEvent() {
        return events.get(events.size() - 1);
    }

    public Object getNthEvent(int index) {
        return events.get(index);
    }
}

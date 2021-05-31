package com.github.nsorin.aramis.utils;

import com.github.nsorin.aramis.observer.EventObserverInterface;

public class MockEventObserver implements EventObserverInterface {

    private Object event;

    @Override
    public <T> void emit(T event) {
        this.event = event;
    }

    public Object getEvent() {
        return event;
    }
}

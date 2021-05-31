package com.github.nsorin.aramis.observer;

public interface EventObserverInterface {
    <T> void emit(T event);
}

package com.github.nsorin.aramis.observer;

public interface ListensTo<E> {
    void on(E event);
}

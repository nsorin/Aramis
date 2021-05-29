package com.github.nsorin.aramis.observer;

import com.github.nsorin.aramis.observer.utils.TestEvent;
import com.github.nsorin.aramis.observer.utils.TestListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventObserverTest {

    private EventObserver observer;

    @BeforeEach
    void setUp() {
        observer = new EventObserver();
    }

    @Test
    void subscribeAndEmit() {
        TestListener listener = new TestListener();

        observer.subscribe(listener, TestEvent.class);
        observer.emit(new TestEvent("Hello"));

        assertEquals("Hello", listener.message);
    }

    @Test
    void subscribeMultipleAndEmit() {
        TestListener firstListener = new TestListener();
        TestListener secondListener = new TestListener();

        observer.subscribe(firstListener, TestEvent.class);
        observer.subscribe(secondListener, TestEvent.class);
        observer.emit(new TestEvent("Hello"));

        assertEquals("Hello", firstListener.message);
        assertEquals("Hello", secondListener.message);
    }

    @Test
    void unsubscribe() {
        TestListener listener = new TestListener();

        observer.subscribe(listener, TestEvent.class);
        observer.emit(new TestEvent("Hello"));
        observer.unsubscribe(listener, TestEvent.class);
        observer.emit(new TestEvent("Hello World!"));

        assertEquals("Hello", listener.message);
    }
}

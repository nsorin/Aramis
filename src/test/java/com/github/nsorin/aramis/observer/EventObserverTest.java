package com.github.nsorin.aramis.observer;

import com.github.nsorin.aramis.observer.utils.InvalidListenerNoParams;
import com.github.nsorin.aramis.observer.utils.TestEvent;
import com.github.nsorin.aramis.observer.utils.TestListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventObserverTest {

    private EventObserver observer;

    @BeforeEach
    void setUp() {
        observer = new EventObserver();
    }

    @Test
    void subscribeAndEmit() throws NoSuchMethodException {
        TestListener listener = new TestListener();

        Method method = TestListener.class.getMethod("onEvent", TestEvent.class);
        observer.subscribe(TestEvent.class, listener, method);
        observer.emit(new TestEvent("Hello"));

        assertEquals("Hello", listener.message);
    }

    @Test
    void subscribeMultipleAndEmit() throws NoSuchMethodException {
        TestListener firstListener = new TestListener();
        TestListener secondListener = new TestListener();

        Method method = TestListener.class.getMethod("onEvent", TestEvent.class);
        observer.subscribe(TestEvent.class, firstListener, method);
        observer.subscribe(TestEvent.class, secondListener, method);
        observer.emit(new TestEvent("Hello"));

        assertEquals("Hello", firstListener.message);
        assertEquals("Hello", secondListener.message);
    }

    @Test
    void unsubscribe() throws NoSuchMethodException {
        TestListener listener = new TestListener();

        Method method = TestListener.class.getMethod("onEvent", TestEvent.class);
        observer.subscribe(TestEvent.class, listener, method);
        observer.emit(new TestEvent("Hello"));
        observer.unsubscribe(TestEvent.class, listener, method);
        observer.emit(new TestEvent("Hello World!"));

        assertEquals("Hello", listener.message);
    }

    @Test
    void subscribeAndEmit_throwsExceptionIfMethodCallFails() throws NoSuchMethodException {
        InvalidListenerNoParams invalidListener = new InvalidListenerNoParams();

        Method method = InvalidListenerNoParams.class.getMethod("someMethod");
        observer.subscribe(TestEvent.class, invalidListener, method);

        ListenerNotCallableException exception = assertThrows(
                ListenerNotCallableException.class,
                () -> observer.emit(new TestEvent("Hello"))
        );

        assertEquals(
                "Could not call method someMethod in "
                        + InvalidListenerNoParams.class
                        + " with event of type " + TestEvent.class,
                exception.getMessage()
        );
    }
}

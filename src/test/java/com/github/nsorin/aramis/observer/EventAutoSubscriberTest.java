package com.github.nsorin.aramis.observer;

import com.github.nsorin.aramis.observer.utils.InvalidListenerNoParams;
import com.github.nsorin.aramis.observer.utils.TestEvent;
import com.github.nsorin.aramis.observer.utils.TestListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventAutoSubscriberTest {

    private EventAutoSubscriber autoSubscriber;

    @BeforeEach
    void setUp() {
        EventObserver.resetObserver();
        autoSubscriber = new EventAutoSubscriber(EventObserver.getObserver());
    }

    @Test
    void autoSubscribe() {
        TestListener listener = new TestListener();

        autoSubscriber.apply(listener);
        EventObserver.getObserver().emit(new TestEvent("Hello"));

        assertEquals("Hello", listener.message);
    }

    @Test
    void autoSubscribe_throwsExceptionIfMethodDoesNotHaveExactlyOneParameter() {
        InvalidListenerNoParams listener = new InvalidListenerNoParams();

        InvalidEventListenerException exception = assertThrows(
                InvalidEventListenerException.class,
                () -> autoSubscriber.apply(listener)
        );

        assertEquals(
                "Method someMethod in "
                        + InvalidListenerNoParams.class
                        + " must have exactly one parameter to be registered as an Event Listener",
                exception.getMessage()
        );
    }
}

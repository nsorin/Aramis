package com.github.nsorin.aramis.observer;

import com.github.nsorin.aramis.observer.utils.TestEvent;
import com.github.nsorin.aramis.observer.utils.TestListener;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class EventListenerTest {

    @Test
    void constructWithObjectAndMethod() throws NoSuchMethodException {
        TestListener object = new TestListener();
        Method method = object.getClass().getMethod("onEvent", TestEvent.class);
        EventListener eventListener = new EventListener(object, method);

    }
}

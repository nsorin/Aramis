package com.github.nsorin.aramis.observer.utils;

import com.github.nsorin.aramis.observer.OnEvent;

public class TestListener {

    public String message;

    @OnEvent
    public void onEvent(TestEvent event) {
        message = event.message();
    }
}

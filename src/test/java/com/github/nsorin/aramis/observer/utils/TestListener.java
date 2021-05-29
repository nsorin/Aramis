package com.github.nsorin.aramis.observer.utils;

import com.github.nsorin.aramis.observer.ListensTo;

public class TestListener implements ListensTo<TestEvent> {

    public String message;

    @Override
    public void on(TestEvent event) {
        message = event.message();
    }
}

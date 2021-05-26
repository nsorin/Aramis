package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injected;

public class ConstructorInjectionClient {

    private final TestService testService;

    @Injected
    public ConstructorInjectionClient(TestService testService) {
        this.testService = testService;
    }

    public TestService getTestService() {
        return testService;
    }

}

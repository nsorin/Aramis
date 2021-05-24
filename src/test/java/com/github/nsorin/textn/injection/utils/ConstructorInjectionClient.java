package com.github.nsorin.textn.injection.utils;

import com.github.nsorin.textn.injection.Injected;

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

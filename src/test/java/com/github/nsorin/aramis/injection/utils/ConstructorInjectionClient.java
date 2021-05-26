package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injectable;

public class ConstructorInjectionClient {

    private final TestService testService;

    @Injectable
    public ConstructorInjectionClient(TestService testService) {
        this.testService = testService;
    }

    public TestService getTestService() {
        return testService;
    }

}

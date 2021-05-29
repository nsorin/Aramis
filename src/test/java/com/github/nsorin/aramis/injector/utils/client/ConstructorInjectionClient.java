package com.github.nsorin.aramis.injector.utils.client;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.injector.utils.service.TestService;

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

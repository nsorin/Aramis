package com.github.nsorin.aramis.injection.utils.client;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.injection.utils.service.TestService;

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

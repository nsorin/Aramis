package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injected;

public class SetterInjectionClient {

    private TestService testService;

    public TestService getTestService() {
        return testService;
    }

    @Injected
    public void setTestService(TestService testService) {
        this.testService = testService;
    }


}

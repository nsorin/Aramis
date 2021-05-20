package com.github.nsorin.textn.injection.utils;

import com.github.nsorin.textn.injection.Injected;

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

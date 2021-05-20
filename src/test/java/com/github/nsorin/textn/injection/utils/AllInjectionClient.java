package com.github.nsorin.textn.injection.utils;

import com.github.nsorin.textn.injection.Injected;

public class AllInjectionClient {

    private TestService testService;

    @Injected
    public TestService testService2;

    public TestService getTestService() {
        return testService;
    }

    @Injected
    public void setTestService(TestService testService) {
        this.testService = testService;
    }


}

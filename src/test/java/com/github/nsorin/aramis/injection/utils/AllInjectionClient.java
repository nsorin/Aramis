package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injected;

public class AllInjectionClient {

    private TestService setterService;

    @Injected
    public TestService publicFieldService;

    @Injected
    private TestService privateFieldService;

    private final TestService constructorService;

    @Injected
    public AllInjectionClient(TestService constructorService) {
        this.constructorService = constructorService;
    }

    public TestService getPrivateFieldService() {
        return privateFieldService;
    }

    public TestService getSetterService() {
        return setterService;
    }

    @Injected
    public void setSetterService(TestService testService) {
        this.setterService = testService;
    }

    public TestService getConstructorService() {
        return constructorService;
    }
}

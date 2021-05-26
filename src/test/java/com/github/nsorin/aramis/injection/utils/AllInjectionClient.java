package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injectable;

public class AllInjectionClient {

    private TestService setterService;

    @Injectable
    public TestService publicFieldService;

    @Injectable
    private TestService privateFieldService;

    private final TestService constructorService;

    @Injectable
    public AllInjectionClient(TestService constructorService) {
        this.constructorService = constructorService;
    }

    public TestService getPrivateFieldService() {
        return privateFieldService;
    }

    public TestService getSetterService() {
        return setterService;
    }

    @Injectable
    public void setSetterService(TestService testService) {
        this.setterService = testService;
    }

    public TestService getConstructorService() {
        return constructorService;
    }
}

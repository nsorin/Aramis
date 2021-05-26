package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injected;

public class NestedServiceImpl implements NestedService {

    private final TestService testServiceConstructor;

    @Injected
    private TestService testServicePrivateField;

    private TestService testServiceSetter;

    @Injected
    public NestedServiceImpl(TestService testServiceConstructor) {
        this.testServiceConstructor = testServiceConstructor;
    }

    @Injected
    public void setTestServiceSetter(TestService testServiceSetter) {
        this.testServiceSetter = testServiceSetter;
    }

    public TestService getTestServiceConstructor() {
        return testServiceConstructor;
    }

    public TestService getTestServicePrivateField() {
        return testServicePrivateField;
    }

    public TestService getTestServiceSetter() {
        return testServiceSetter;
    }
}

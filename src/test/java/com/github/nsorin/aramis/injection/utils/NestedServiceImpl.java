package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injectable;

public class NestedServiceImpl implements NestedService {

    private final TestService testServiceConstructor;

    @Injectable
    private TestService testServicePrivateField;

    private TestService testServiceSetter;

    @Injectable
    public NestedServiceImpl(TestService testServiceConstructor) {
        this.testServiceConstructor = testServiceConstructor;
    }

    @Injectable
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

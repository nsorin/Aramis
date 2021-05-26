package com.github.nsorin.aramis.injection.utils.client;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.injection.utils.service.NestedService;
import com.github.nsorin.aramis.injection.utils.service.TestService;

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

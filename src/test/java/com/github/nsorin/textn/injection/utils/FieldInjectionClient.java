package com.github.nsorin.textn.injection.utils;

import com.github.nsorin.textn.injection.Injected;

public class FieldInjectionClient {
    @Injected
    public TestService testService;

    @Injected
    private TestService privateTestService;

    public TestService getPrivateTestService() {
        return privateTestService;
    }

}

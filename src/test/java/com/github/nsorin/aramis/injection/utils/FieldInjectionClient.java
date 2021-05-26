package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injected;

public class FieldInjectionClient {
    @Injected
    public TestService testService;

    @Injected
    private TestService privateTestService;

    public TestService getPrivateTestService() {
        return privateTestService;
    }

}

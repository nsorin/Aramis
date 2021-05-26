package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injectable;

public class FieldInjectionClient {
    @Injectable
    public TestService testService;

    @Injectable
    private TestService privateTestService;

    public TestService getPrivateTestService() {
        return privateTestService;
    }

}

package com.github.nsorin.aramis.injector.utils.client;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.injector.utils.service.TestService;

public class FieldInjectionClient {
    @Injectable
    public TestService testService;

    @Injectable
    private TestService privateTestService;

    public TestService getPrivateTestService() {
        return privateTestService;
    }

}

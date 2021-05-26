package com.github.nsorin.aramis.injection.utils.client;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.injection.utils.service.TestService;

public class FieldInjectionClient {
    @Injectable
    public TestService testService;

    @Injectable
    private TestService privateTestService;

    public TestService getPrivateTestService() {
        return privateTestService;
    }

}

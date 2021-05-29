package com.github.nsorin.aramis.injector.utils.client;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.injector.utils.service.TestService;

public class SetterInjectionClient {

    private TestService testService;

    public TestService getTestService() {
        return testService;
    }

    @Injectable
    public void setTestService(TestService testService) {
        this.testService = testService;
    }

}

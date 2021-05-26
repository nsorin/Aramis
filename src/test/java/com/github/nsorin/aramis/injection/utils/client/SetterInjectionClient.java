package com.github.nsorin.aramis.injection.utils.client;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.injection.utils.service.TestService;

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

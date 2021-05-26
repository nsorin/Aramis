package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injectable;

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

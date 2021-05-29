package com.github.nsorin.aramis.injector.utils.client;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.injector.utils.service.TestServiceImpl;

public class DirectImplementationClient {

    @Injectable
    public TestServiceImpl publicFieldService;

    private final TestServiceImpl constructorService;

    @Injectable
    public DirectImplementationClient(TestServiceImpl constructorService) {
        this.constructorService = constructorService;
    }

    public TestServiceImpl getConstructorService() {
        return constructorService;
    }
}

package com.github.nsorin.aramis.injection.utils.client;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.injection.utils.service.TestServiceImpl;

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

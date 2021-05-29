package com.github.nsorin.aramis.injector.utils.service;

import com.github.nsorin.aramis.injector.Injectable;

public class NestedServiceClient {

    private final NestedService nestedService;

    @Injectable
    public NestedServiceClient(NestedService nestedService) {
        this.nestedService = nestedService;
    }

    public NestedService getNestedService() {
        return nestedService;
    }
}

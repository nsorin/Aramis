package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injected;

public class NestedServiceClient {

    private final NestedService nestedService;

    @Injected
    public NestedServiceClient(NestedService nestedService) {
        this.nestedService = nestedService;
    }

    public NestedService getNestedService() {
        return nestedService;
    }
}

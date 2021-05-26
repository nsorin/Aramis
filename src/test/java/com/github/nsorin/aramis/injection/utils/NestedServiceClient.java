package com.github.nsorin.aramis.injection.utils;

import com.github.nsorin.aramis.injection.Injectable;

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

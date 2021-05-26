package com.github.nsorin.aramis.injection.utils.service;

public interface NestedService {
    TestService getTestServiceConstructor();

    TestService getTestServicePrivateField();

    TestService getTestServiceSetter();
}

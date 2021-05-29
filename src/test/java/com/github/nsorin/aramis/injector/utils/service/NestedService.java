package com.github.nsorin.aramis.injector.utils.service;

public interface NestedService {
    TestService getTestServiceConstructor();

    TestService getTestServicePrivateField();

    TestService getTestServiceSetter();
}

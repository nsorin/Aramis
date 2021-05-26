package com.github.nsorin.aramis.injection.utils;

public interface NestedService {
    TestService getTestServiceConstructor();

    TestService getTestServicePrivateField();

    TestService getTestServiceSetter();
}

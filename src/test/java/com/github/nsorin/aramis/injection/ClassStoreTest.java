package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.TestService;
import com.github.nsorin.aramis.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassStoreTest {

    ClassStore store;

    @BeforeEach
    void setUp() {
        store = new ClassStore();
    }

    @Test
    void getImplementationClass() {
        store.register(TestService.class, TestServiceImpl.class);

        assertEquals(TestServiceImpl.class, store.getImplementationClass(TestService.class));
    }
}

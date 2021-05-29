package com.github.nsorin.aramis.injector;

import com.github.nsorin.aramis.injector.utils.service.TestService;
import com.github.nsorin.aramis.injector.utils.service.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void getImplementationClassThrowsErrorIfNotRegistered() {
        TypeNotRegisteredException exception = assertThrows(
                TypeNotRegisteredException.class,
                () -> store.getImplementationClass(TestService.class)
        );

        assertEquals(
                TestService.class + " has not been registered in the Dependency Provider.",
                exception.getMessage()
        );
    }
}

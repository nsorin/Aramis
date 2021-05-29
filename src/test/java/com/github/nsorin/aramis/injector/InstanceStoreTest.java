package com.github.nsorin.aramis.injector;

import com.github.nsorin.aramis.injector.utils.service.TestService;
import com.github.nsorin.aramis.injector.utils.service.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstanceStoreTest {

    InstanceStore store;

    @BeforeEach
    void setUp() {
        store = new InstanceStore();
    }

    @Test
    void getImplementationInstance() {
        TestServiceImpl instance = new TestServiceImpl();
        store.register(TestService.class, instance);

        TestService resultInstance = store.getImplementationInstance(TestService.class);
        assertSame(instance, resultInstance);
    }

    @Test
    void isRegistered() {
        TestServiceImpl instance = new TestServiceImpl();
        store.register(TestService.class, instance);

        assertTrue(store.isRegistered(TestService.class));
        assertFalse(store.isRegistered(TestServiceImpl.class));
    }

    @Test
    void isInitialized() {
        TestServiceImpl instance = new TestServiceImpl();
        store.register(TestService.class, instance);
        store.register(TestServiceImpl.class, null);

        assertTrue(store.isInitialized(TestService.class));
        assertFalse(store.isInitialized(TestServiceImpl.class));
    }
}

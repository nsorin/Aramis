package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ClassStoreTest {

    ClassStore store;

    @BeforeEach
    void setUp() {
        store = new ClassStore();
    }

    @Test
    void registerInstanceAndResolve() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestService registered = new TestServiceImpl();
        store.registerInstance(TestService.class, registered);

        TestService resolved = store.resolve(TestService.class);

        assertSame(registered, resolved);
    }

    @Test
    void registerAndResolve() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        store.register(TestService.class, TestServiceImpl.class);

        TestService resolved = store.resolve(TestService.class);

        assertTrue(resolved instanceof TestServiceImpl);
    }

    @Test
    void resolveImplementationDirectlyIfNotRegistered() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        assertNotNull(store.resolve(TestServiceImpl.class));
    }

    @Test
    void throwExceptionIfInterfaceHasNoRegisteredImplementation() {
        assertThrows(
                InterfaceNotRegisteredException.class,
                () -> store.resolve(TestService.class)
        );
    }
}

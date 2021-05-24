package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectorTest {

    @Test
    void injectAllDependencies() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = injector.createWithDependencies(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.getConstructorService());
        assertTrue(client.getConstructorService() instanceof TestServiceImpl);
        assertNotNull(client.getPrivateFieldService());
        assertTrue(client.getPrivateFieldService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);
    }

    @Test
    void injectAllDependencies_throwExceptionIfConstructorNotAnnotated() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        DependencyInjectionException exception = assertThrows(
                DependencyInjectionException.class,
                () -> injector.createWithDependencies(MissingConstructorAnnotationClient.class)
        );
        assertEquals(
                "Cannot resolve dependencies for " + MissingConstructorAnnotationClient.class,
                exception.getMessage()
        );
    }

    @Test
    void injectAllDependencies_throwExceptionIfInvalidDependencyType() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        DependencyInjectionException exception = assertThrows(
                DependencyInjectionException.class,
                () -> injector.createWithDependencies(InvalidTypeClient.class)
        );
        assertEquals(
                "Cannot resolve dependencies for " + InvalidTypeClient.class,
                exception.getMessage()
        );
    }
}

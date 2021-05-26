package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectorTest {

    @Test
    void injectAllDependencies() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = injector.resolve(AllInjectionClient.class);

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
                () -> injector.resolve(MissingConstructorAnnotationClient.class)
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
                () -> injector.resolve(InvalidTypeClient.class)
        );
        assertEquals(
                "Cannot resolve dependencies for " + InvalidTypeClient.class,
                exception.getMessage()
        );
    }

    @Test
    void injectAllDependenciesTwoLevels() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);
        injector.getStore().register(NestedService.class, NestedServiceImpl.class);

        NestedServiceClient client = injector.resolve(NestedServiceClient.class);

        assertNotNull(client.getNestedService());
        assertNotNull(client.getNestedService().getTestServiceConstructor());
        assertNotNull(client.getNestedService().getTestServicePrivateField());
        assertNotNull(client.getNestedService().getTestServiceSetter());
    }
}

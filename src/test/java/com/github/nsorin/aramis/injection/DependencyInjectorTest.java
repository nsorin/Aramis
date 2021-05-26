package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.client.AllInjectionClient;
import com.github.nsorin.aramis.injection.utils.client.InvalidTypeClient;
import com.github.nsorin.aramis.injection.utils.client.MissingConstructorAnnotationClient;
import com.github.nsorin.aramis.injection.utils.client.NestedServiceImpl;
import com.github.nsorin.aramis.injection.utils.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectorTest {

    @Test
    void resolve() {
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
    void resolve_throwExceptionIfConstructorNotAnnotated() {
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
    void resolve_throwExceptionIfInvalidDependencyType() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        TypeNotRegisteredException exception = assertThrows(
                TypeNotRegisteredException.class,
                () -> injector.resolve(InvalidTypeClient.class)
        );
        assertEquals(
                "int has not been registered in the Dependency Provider.",
                exception.getMessage()
        );
    }

    @Test
    void resolveTwoLevels() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);
        injector.getStore().register(NestedService.class, NestedServiceImpl.class);

        NestedServiceClient client = injector.resolve(NestedServiceClient.class);

        assertNotNull(client.getNestedService());
        assertNotNull(client.getNestedService().getTestServiceConstructor());
        assertNotNull(client.getNestedService().getTestServicePrivateField());
        assertNotNull(client.getNestedService().getTestServiceSetter());
    }

    @Test
    void resolve_throwsExceptionForCircularDependency() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(CircularDependencyService.class, CircularDependencyService.class);

        MaxDependencyDepthReachedException exception = assertThrows(
                MaxDependencyDepthReachedException.class,
                () -> injector.resolve(CircularDependencyService.class)
        );

        assertEquals(
                "Maximum dependency depth of 10 reached while resolving "
                        + CircularDependencyService.class
                        + ". Please check for possible dependency cycles.",
                exception.getMessage()
        );
    }
}

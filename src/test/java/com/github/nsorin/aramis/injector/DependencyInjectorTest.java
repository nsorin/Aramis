package com.github.nsorin.aramis.injector;

import com.github.nsorin.aramis.injector.utils.client.AllInjectionClient;
import com.github.nsorin.aramis.injector.utils.client.InvalidTypeClient;
import com.github.nsorin.aramis.injector.utils.client.MissingConstructorAnnotationClient;
import com.github.nsorin.aramis.injector.utils.client.NestedServiceImpl;
import com.github.nsorin.aramis.injector.utils.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectorTest {

    @Test
    void registerAndResolve() {
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        injector.register(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = injector.resolve(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.getConstructorService());
        assertTrue(client.getConstructorService() instanceof TestServiceImpl);
        assertNotNull(client.getPrivateFieldService());
        assertTrue(client.getPrivateFieldService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertNotSame(client.getSetterService(), client.getConstructorService());
        assertNotSame(client.getSetterService(), client.getPrivateFieldService());
        assertNotSame(client.getSetterService(), client.publicFieldService);
    }

    @Test
    void registerAndResolveSingleton() {
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        injector.registerSingleton(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = injector.resolve(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.getConstructorService());
        assertTrue(client.getConstructorService() instanceof TestServiceImpl);
        assertNotNull(client.getPrivateFieldService());
        assertTrue(client.getPrivateFieldService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertSame(client.getSetterService(), client.getConstructorService());
        assertSame(client.getSetterService(), client.getPrivateFieldService());
        assertSame(client.getSetterService(), client.publicFieldService);
    }

    @Test
    void registerAndResolveInstance() {
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        TestServiceImpl instance = new TestServiceImpl();
        injector.registerInstance(TestService.class, instance);

        AllInjectionClient client = injector.resolve(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.getConstructorService());
        assertTrue(client.getConstructorService() instanceof TestServiceImpl);
        assertNotNull(client.getPrivateFieldService());
        assertTrue(client.getPrivateFieldService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertSame(instance, client.getSetterService());
        assertSame(instance, client.getConstructorService());
        assertSame(instance, client.getPrivateFieldService());
        assertSame(instance, client.publicFieldService);
    }

    @Test
    void resolve_throwExceptionIfConstructorNotAnnotated() {
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        injector.register(TestService.class, TestServiceImpl.class);

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
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        injector.register(TestService.class, TestServiceImpl.class);

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
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        injector.register(TestService.class, TestServiceImpl.class);
        injector.register(NestedService.class, NestedServiceImpl.class);

        NestedServiceClient client = injector.resolve(NestedServiceClient.class);

        assertNotNull(client.getNestedService());
        assertNotNull(client.getNestedService().getTestServiceConstructor());
        assertNotNull(client.getNestedService().getTestServicePrivateField());
        assertNotNull(client.getNestedService().getTestServiceSetter());
    }

    @Test
    void resolve_throwsExceptionForCircularDependency() {
        DependencyInjector injector = new DependencyInjector(new ClassStore(), new InstanceStore());
        injector.register(CircularDependencyService.class, CircularDependencyService.class);

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

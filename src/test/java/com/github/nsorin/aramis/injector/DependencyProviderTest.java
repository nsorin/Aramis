package com.github.nsorin.aramis.injector;

import com.github.nsorin.aramis.injector.utils.client.AllInjectionClient;
import com.github.nsorin.aramis.injector.utils.client.DirectImplementationClient;
import com.github.nsorin.aramis.injector.utils.service.TestService;
import com.github.nsorin.aramis.injector.utils.service.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyProviderTest {

    @BeforeEach
    void setUp() {
        DependencyProvider.resetProvider();
    }

    @Test
    void getDependencyProvider() {
        assertNotNull(DependencyProvider.getProvider());
    }

    @Test
    void provideAndInjectServiceImplementation() {
        DependencyProvider.getProvider().provide(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = DependencyProvider.getProvider().resolve(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertNotSame(client.getSetterService(), client.publicFieldService);
    }

    @Test
    void provideAndInjectServiceImplementationWithoutInterface() {
        DependencyProvider.getProvider().provide(TestServiceImpl.class);

        DirectImplementationClient client = DependencyProvider.getProvider().resolve(DirectImplementationClient.class);

        assertNotNull(client.publicFieldService);
        assertNotNull(client.getConstructorService());
        assertNotSame(client.publicFieldService, client.getConstructorService());
    }

    @Test
    void provideAndInjectSingletonServiceImplementation() {
        DependencyProvider.getProvider().provideSingleton(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = DependencyProvider.getProvider().resolve(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertSame(client.getSetterService(), client.publicFieldService);
    }

    @Test
    void provideAndInjectServiceSingletonImplementationWithoutInterface() {
        DependencyProvider.getProvider().provideSingleton(TestServiceImpl.class);

        DirectImplementationClient client = DependencyProvider.getProvider().resolve(DirectImplementationClient.class);

        assertNotNull(client.publicFieldService);
        assertNotNull(client.getConstructorService());
        assertSame(client.publicFieldService, client.getConstructorService());
    }

    @Test
    void provideAndInjectServiceInstance() {
        TestServiceImpl instance = new TestServiceImpl();
        DependencyProvider.getProvider().provideInstance(TestService.class, instance);

        AllInjectionClient client = DependencyProvider.getProvider().resolve(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertSame(instance, client.publicFieldService);
        assertSame(instance, client.getSetterService());
    }

    @Test
    void provideAndInjectServiceInstanceWithoutInterface() {
        TestServiceImpl instance = new TestServiceImpl();
        DependencyProvider.getProvider().provideInstance(instance);

        DirectImplementationClient client = DependencyProvider.getProvider().resolve(DirectImplementationClient.class);

        assertNotNull(client.publicFieldService);
        assertNotNull(client.getConstructorService());
        assertSame(instance, client.publicFieldService);
        assertSame(instance, client.getConstructorService());
    }
}

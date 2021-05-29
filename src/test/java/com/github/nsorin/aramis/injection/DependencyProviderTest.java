package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.client.AllInjectionClient;
import com.github.nsorin.aramis.injection.utils.service.TestService;
import com.github.nsorin.aramis.injection.utils.service.TestServiceImpl;
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

        ControllerFactory controllerFactory = DependencyProvider.getProvider().getControllerFactory();
        AllInjectionClient client = (AllInjectionClient) controllerFactory.call(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertNotSame(client.getSetterService(), client.publicFieldService);
    }

    @Test
    void provideAndInjectSingletonServiceImplementation() {
        DependencyProvider.getProvider().provideSingleton(TestService.class, TestServiceImpl.class);

        ControllerFactory controllerFactory = DependencyProvider.getProvider().getControllerFactory();
        AllInjectionClient client = (AllInjectionClient) controllerFactory.call(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertSame(client.getSetterService(), client.publicFieldService);
    }

    @Test
    void provideAndInjectServiceInstance() {
        TestServiceImpl instance = new TestServiceImpl();
        DependencyProvider.getProvider().provideInstance(TestService.class, instance);

        ControllerFactory controllerFactory = DependencyProvider.getProvider().getControllerFactory();
        AllInjectionClient client = (AllInjectionClient) controllerFactory.call(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);

        assertSame(instance, client.publicFieldService);
        assertSame(instance, client.getSetterService());
    }
}

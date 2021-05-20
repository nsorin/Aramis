package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.AllInjectionClient;
import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DependencyProviderTest {

    @Test
    void getDependencyProvider() {
        assertNotNull(DependencyProvider.getProvider());
    }

    @Test
    void provideAndInjectServiceImplementation() {
        DependencyProvider provider = DependencyProvider.getProvider();
        provider.provide(TestService.class, TestServiceImpl.class);

        ControllerFactory controllerFactory = provider.getControllerFactory();
        AllInjectionClient client = (AllInjectionClient) controllerFactory.call(AllInjectionClient.class);

        assertNotNull(client.getTestService());
        assertTrue(client.getTestService() instanceof TestServiceImpl);
        assertNotNull(client.testService2);
        assertTrue(client.testService2 instanceof TestServiceImpl);
    }
}

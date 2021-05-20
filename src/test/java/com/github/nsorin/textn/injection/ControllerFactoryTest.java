package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.AllInjectionClient;
import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerFactoryTest {

    @Test
    void returnsControllerWithInjectedDependencies() {
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        ControllerFactory controllerFactory = new ControllerFactory(new DependencyInjector(store));
        AllInjectionClient client = (AllInjectionClient) controllerFactory.call(AllInjectionClient.class);

        assertNotNull(client.getTestService());
        assertTrue(client.getTestService() instanceof TestServiceImpl);
        assertNotNull(client.testService2);
        assertTrue(client.testService2 instanceof TestServiceImpl);
    }
}

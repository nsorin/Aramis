package com.github.nsorin.aramis.injector;

import com.github.nsorin.aramis.injector.utils.client.AllInjectionClient;
import com.github.nsorin.aramis.injector.utils.service.TestService;
import com.github.nsorin.aramis.injector.utils.service.TestServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerFactoryTest {

    @Test
    void returnsControllerWithInjectedDependencies() {
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        ControllerFactory controllerFactory = new ControllerFactory(new DependencyInjector(store, new InstanceStore()));
        AllInjectionClient client = (AllInjectionClient) controllerFactory.call(AllInjectionClient.class);

        assertNotNull(client.getSetterService());
        assertTrue(client.getSetterService() instanceof TestServiceImpl);
        assertNotNull(client.publicFieldService);
        assertTrue(client.publicFieldService instanceof TestServiceImpl);
    }
}

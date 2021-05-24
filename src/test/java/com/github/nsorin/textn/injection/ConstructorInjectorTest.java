package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.ConstructorInjectionClient;
import com.github.nsorin.textn.injection.utils.NoDependencyClient;
import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorInjectorTest {

    @Test
    void canConstructClientWithInjectedService() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        ConstuctorInjector injector = new ConstuctorInjector(store);

        ConstructorInjectionClient client = injector.createAndInject(ConstructorInjectionClient.class);

        assertNotNull(client.getTestService());
        assertTrue(client.getTestService() instanceof TestServiceImpl);
    }

    @Test
    void canConstructClientWithoutDependenciesUsingDefaultConstructor() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ClassStore store = new ClassStore();
        ConstuctorInjector injector = new ConstuctorInjector(store);

        NoDependencyClient client = injector.createAndInject(NoDependencyClient.class);

        assertNotNull(client);
    }
}

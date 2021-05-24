package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.FieldInjectionClient;
import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldInjectorTest {

    @Test
    void injectDependenciesPublicField() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FieldInjectionClient client = new FieldInjectionClient();
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        FieldInjector injector = new FieldInjector(store);

        injector.inject(client);

        assertNotNull(client.testService);
        assertTrue(client.testService instanceof TestServiceImpl);
    }

    @Test
    void injectDependenciesPrivateField() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FieldInjectionClient client = new FieldInjectionClient();
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        FieldInjector injector = new FieldInjector(store);

        injector.inject(client);

        assertNotNull(client.getPrivateTestService());
        assertTrue(client.getPrivateTestService() instanceof TestServiceImpl);
    }
}

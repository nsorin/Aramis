package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.SetterInjectionClient;
import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetterInjectorTest {

    @Test
    void injectDependenciesOneArgument() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SetterInjectionClient client = new SetterInjectionClient();
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        SetterInjector injector = new SetterInjector(store);

        injector.inject(client);

        assertNotNull(client.getTestService());
        assertTrue(client.getTestService() instanceof TestServiceImpl);
    }
}

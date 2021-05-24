package com.github.nsorin.textn.injection;

import com.github.nsorin.textn.injection.utils.AllInjectionClient;
import com.github.nsorin.textn.injection.utils.MissingConstructorAnnotationClient;
import com.github.nsorin.textn.injection.utils.TestService;
import com.github.nsorin.textn.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectorTest {

    @Test
    void injectAllDependencies() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        AllInjectionClient client = injector.createWithDependencies(AllInjectionClient.class);

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
    void injectAllDependenciesThrowsExceptionIfInvalid() {
        DependencyInjector injector = new DependencyInjector(new ClassStore());
        injector.getStore().register(TestService.class, TestServiceImpl.class);

        assertThrows(DependencyInjectionException.class, () -> injector.createWithDependencies(MissingConstructorAnnotationClient.class));
    }
}

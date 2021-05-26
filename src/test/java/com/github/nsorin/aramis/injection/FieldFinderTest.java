package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.FieldInjectionClient;
import com.github.nsorin.aramis.injection.utils.TestService;
import com.github.nsorin.aramis.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldFinderTest {

    @Test
    void injectDependenciesPublicField() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FieldInjectionClient client = new FieldInjectionClient();
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        FieldFinder finder = new FieldFinder(store);

        finder.inject(client);

        assertNotNull(client.testService);
        assertTrue(client.testService instanceof TestServiceImpl);
    }

    @Test
    void injectDependenciesPrivateField() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FieldInjectionClient client = new FieldInjectionClient();
        ClassStore store = new ClassStore();
        store.register(TestService.class, TestServiceImpl.class);
        FieldFinder finder = new FieldFinder(store);

        finder.inject(client);

        assertNotNull(client.getPrivateTestService());
        assertTrue(client.getPrivateTestService() instanceof TestServiceImpl);
    }

    @Test
    void findInjectableFields() {
        FieldInjectionClient client = new FieldInjectionClient();
        ClassStore store = new ClassStore();
        FieldFinder finder = new FieldFinder(store);

        List<Field> injectableFields = finder.findInjectableFields(client);

        assertEquals(2, injectableFields.size());
    }
}

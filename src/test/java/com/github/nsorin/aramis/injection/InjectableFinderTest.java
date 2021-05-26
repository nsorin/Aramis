package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InjectableFinderTest {

    @Test
    void findInjectableConstructor() throws NoSuchMethodException {
        InjectableFinder finder = new InjectableFinder();

        Constructor<ConstructorInjectionClient> injectableConstructor = finder.findInjectableConstructor(ConstructorInjectionClient.class);

        assertNotNull(injectableConstructor);
        assertEquals(1, injectableConstructor.getParameterCount());
        assertEquals(TestService.class, injectableConstructor.getParameterTypes()[0]);
    }

    @Test
    void findInjectableConstructorFallsBackToDefault() throws NoSuchMethodException {
        InjectableFinder finder = new InjectableFinder();

        Constructor<TestServiceImpl> injectableConstructor = finder.findInjectableConstructor(TestServiceImpl.class);

        assertNotNull(injectableConstructor);
        assertEquals(0, injectableConstructor.getParameterCount());
    }

    @Test
    void findInjectableFields() {
        FieldInjectionClient client = new FieldInjectionClient();
        InjectableFinder finder = new InjectableFinder();

        List<Field> injectableFields = finder.findInjectableFields(client);

        assertEquals(2, injectableFields.size());
    }

    @Test
    void findInjectableSetters() {
        SetterInjectionClient client = new SetterInjectionClient();
        InjectableFinder finder = new InjectableFinder();

        List<Method> injectableSetters = finder.findInjectableSetters(client);

        assertEquals(1, injectableSetters.size());
    }
}

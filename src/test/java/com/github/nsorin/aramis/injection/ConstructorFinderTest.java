package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.ConstructorInjectionClient;
import com.github.nsorin.aramis.injection.utils.TestService;
import com.github.nsorin.aramis.injection.utils.TestServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConstructorFinderTest {

    @Test
    void findInjectableConstructor() throws NoSuchMethodException {
        ConstuctorFinder finder = new ConstuctorFinder();

        Constructor<ConstructorInjectionClient> injectableConstructor = finder.findInjectableConstructor(ConstructorInjectionClient.class);

        assertNotNull(injectableConstructor);
        assertEquals(1, injectableConstructor.getParameterCount());
        assertEquals(TestService.class, injectableConstructor.getParameterTypes()[0]);
    }

    @Test
    void findInjectableConstructorFallsBackToDefault() throws NoSuchMethodException {
        ConstuctorFinder finder = new ConstuctorFinder();

        Constructor<TestServiceImpl> injectableConstructor = finder.findInjectableConstructor(TestServiceImpl.class);

        assertNotNull(injectableConstructor);
        assertEquals(0, injectableConstructor.getParameterCount());
    }
}

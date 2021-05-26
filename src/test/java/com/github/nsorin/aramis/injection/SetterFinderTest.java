package com.github.nsorin.aramis.injection;

import com.github.nsorin.aramis.injection.utils.SetterInjectionClient;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetterFinderTest {

    @Test
    void findInjectableSetters() {
        SetterInjectionClient client = new SetterInjectionClient();
        ClassStore store = new ClassStore();
        SetterFinder finder = new SetterFinder();

        List<Method> injectableSetters = finder.findInjectableSetters(client);

        assertEquals(1, injectableSetters.size());

    }
}

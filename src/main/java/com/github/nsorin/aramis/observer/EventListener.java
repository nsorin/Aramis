package com.github.nsorin.aramis.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

record EventListener(Object object, Method method) {

    void call(Object event) throws InvocationTargetException, IllegalAccessException {
        method.invoke(object, event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventListener that = (EventListener) o;
        return object.equals(that.object) && method.equals(that.method);
    }
}

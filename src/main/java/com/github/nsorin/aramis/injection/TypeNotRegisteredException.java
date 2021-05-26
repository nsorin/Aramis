package com.github.nsorin.aramis.injection;

public class TypeNotRegisteredException extends RuntimeException {
    private final Class<?> type;

    public TypeNotRegisteredException(Class<?> type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return type + " has not been registered in the Dependency Provider.";
    }
}

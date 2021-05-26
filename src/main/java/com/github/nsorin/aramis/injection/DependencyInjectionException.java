package com.github.nsorin.aramis.injection;

class DependencyInjectionException extends RuntimeException {
    private final Class<?> type;

    public DependencyInjectionException(Class<?> type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "Cannot resolve dependencies for " + type;
    }
}
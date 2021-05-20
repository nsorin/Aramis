package com.github.nsorin.textn.injection;

public class DependencyProvider {

    private final DependencyInjector dependencyInjector;
    private final ControllerFactory controllerFactory;

    public static DependencyProvider getProvider() {
        return new DependencyProvider(new DependencyInjector(new ClassStore()));
    }

    private DependencyProvider(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
        this.controllerFactory = new ControllerFactory(dependencyInjector);
    }

    public <T, U extends T> void provide(Class<T> serviceInterface, Class<U> serviceImplementation) {
        this.dependencyInjector.getStore().register(serviceInterface, serviceImplementation);
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }
}

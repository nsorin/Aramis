package com.github.nsorin.aramis.injector;

public class DependencyProvider {

    private static DependencyProvider provider;

    private final DependencyInjector dependencyInjector;
    private final ControllerFactory controllerFactory;

    public static void resetProvider() {
        provider = null;
    }

    public static DependencyProvider getProvider() {
        if (provider == null) {
            provider = new DependencyProvider(new DependencyInjector(new ClassStore(), new InstanceStore()));
        }
        return provider;
    }

    private DependencyProvider(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
        controllerFactory = new ControllerFactory(dependencyInjector);
    }

    public <T> void provide(Class<T> serviceImplementation) {
        provide(serviceImplementation, serviceImplementation);
    }

    public <T, U extends T> void provide(Class<T> serviceInterface, Class<U> serviceImplementation) {
        dependencyInjector.register(serviceInterface, serviceImplementation);
    }

    public <T> void provideSingleton(Class<T> serviceImplementation) {
        provideSingleton(serviceImplementation, serviceImplementation);
    }

    public <T, U extends T> void provideSingleton(Class<T> serviceInterface, Class<U> serviceImplementation) {
        dependencyInjector.registerSingleton(serviceInterface, serviceImplementation);
    }

    @SuppressWarnings("unchecked")
    public <T> void provideInstance(T serviceInstance) {
        provideInstance((Class<T>) serviceInstance.getClass(), serviceInstance);
    }

    public <T, U extends T> void provideInstance(Class<T> serviceInterface, U serviceInstance) {
        dependencyInjector.registerInstance(serviceInterface, serviceInstance);
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public <T> T resolve(Class<T> type) {
        return dependencyInjector.resolve(type);
    }

}

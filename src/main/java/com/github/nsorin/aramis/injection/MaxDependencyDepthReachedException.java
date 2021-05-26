package com.github.nsorin.aramis.injection;

public class MaxDependencyDepthReachedException extends RuntimeException {

    private final Class<?> type;
    private final int maxDependencyDepth;

    public MaxDependencyDepthReachedException(Class<?> type, int maxDependencyDepth) {
        this.type = type;
        this.maxDependencyDepth = maxDependencyDepth;
    }

    @Override
    public String getMessage() {
        return "Maximum dependency depth of " + maxDependencyDepth + " reached while resolving " + type
                + ". Please check for possible dependency cycles.";
    }
}

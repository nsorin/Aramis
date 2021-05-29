package com.github.nsorin.aramis.model;

public class FileProperties {
    private final String location;
    private final String name;

    public FileProperties() {
        location = null;
        name = null;
    }

    public FileProperties(String location, String name) {
        this.location = location;
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public boolean isNew() {
        return location == null;
    }
}

package com.github.nsorin.aramis.model;

public class FileProperties {
    private final String location;
    private final String name;
    private final boolean axml;

    public FileProperties() {
        this(null, null);
    }

    public FileProperties(String location, String name) {
        this(location, name, false);
    }

    public FileProperties(String location, String name, boolean axml) {
        this.location = location;
        this.name = name;
        this.axml = axml;
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

    public boolean isAXML() {
        return axml;
    }
}

package com.github.nsorin.textn.model;

public class TextFile {
    private final String location;
    private final String name;
    private String content;

    public TextFile(String location, String name, String content) {

        this.location = location;
        this.name = name;
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

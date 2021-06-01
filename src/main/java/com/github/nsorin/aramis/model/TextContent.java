package com.github.nsorin.aramis.model;

public class TextContent {
    private String content;

    public TextContent() {
        this("");
    }

    public TextContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

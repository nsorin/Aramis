package com.github.nsorin.aramis.model;

public class TextContent {
    private String text;

    public TextContent() {
        this("");
    }

    public TextContent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

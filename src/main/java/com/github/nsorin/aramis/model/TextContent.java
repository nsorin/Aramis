package com.github.nsorin.aramis.model;

public class TextContent {
    private final String fileLocation;
    private final String fileName;
    private String content;

    public static TextContent makeNew() {
        return new TextContent(null, null, "");
    }

    public TextContent(String fileLocation, String fileName, String content) {
        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isNew() {
        return fileLocation == null;
    }
}

package com.github.nsorin.textn.model;

public class Text {
    private final String fileLocation;
    private final String fileName;
    private String content;

    public static Text makeNew() {
        return new Text(null, null, "");
    }

    public Text(String fileLocation, String fileName, String content) {
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

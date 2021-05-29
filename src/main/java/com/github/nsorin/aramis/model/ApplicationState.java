package com.github.nsorin.aramis.model;

public class ApplicationState {
    private TextContent textContent;
    private FileProperties fileProperties;

    public ApplicationState() {
        textContent = new TextContent();
        fileProperties = new FileProperties();
    }

    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
    }

    public TextContent getTextContent() {
        return textContent;
    }

    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    public FileProperties getFileProperties() {
        return fileProperties;
    }
}

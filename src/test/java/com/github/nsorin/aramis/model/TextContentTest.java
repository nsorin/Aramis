package com.github.nsorin.aramis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextContentTest {

    @Test
    void constructTextFileWithPathNameAndContent() {
        String fileLocation = "./test.txt";
        String fileName = "test.txt";
        String fileContent = "lorem ipsum";

        TextContent textContent = new TextContent(fileLocation, fileName, fileContent);

        assertEquals(fileLocation, textContent.getFileLocation());
        assertEquals(fileName, textContent.getFileName());
        assertEquals(fileContent, textContent.getContent());
    }

    @Test
    void isNewReturnsTrueIfNoLocation() {
        assertTrue(new TextContent().isNew());
    }

    @Test
    void isNewReturnsFalseIfHasLocation() {
        assertFalse(new TextContent("somepath", "somename", "content").isNew());
    }
}

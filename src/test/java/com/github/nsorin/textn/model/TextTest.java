package com.github.nsorin.textn.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextTest {

    @Test
    void constructTextFileWithPathNameAndContent() {
        String fileLocation = "./test.txt";
        String fileName = "test.txt";
        String fileContent = "lorem ipsum";

        Text text = new Text(fileLocation, fileName, fileContent);

        assertEquals(fileLocation, text.getFileLocation());
        assertEquals(fileName, text.getFileName());
        assertEquals(fileContent, text.getContent());
    }

    @Test
    void isNewReturnsTrueIfNoLocation() {
        assertTrue(Text.makeNew().isNew());
    }

    @Test
    void isNewReturnsFalseIfHasLocation() {
        assertFalse(new Text("somepath", "somename", "content").isNew());
    }
}

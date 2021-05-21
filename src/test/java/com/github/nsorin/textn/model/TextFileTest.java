package com.github.nsorin.textn.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileTest {

    @Test
    void constructTextFileWithPathNameAndContent() {
        String fileLocation = "./test.txt";
        String fileName = "test.txt";
        String fileContent = "lorem ipsum";

        TextFile textFile = new TextFile(fileLocation, fileName, fileContent);

        assertEquals(fileLocation, textFile.getLocation());
        assertEquals(fileName, textFile.getName());
        assertEquals(fileContent, textFile.getContent());
    }

    @Test
    void isNewReturnsTrueIfNoLocation() {
        assertTrue(TextFile.makeNew().isNew());
    }

    @Test
    void isNewReturnsFalseIfHasLocation() {
        assertFalse(new TextFile("somepath", "somename", "content").isNew());
    }
}

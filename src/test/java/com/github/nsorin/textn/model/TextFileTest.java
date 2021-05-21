package com.github.nsorin.textn.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

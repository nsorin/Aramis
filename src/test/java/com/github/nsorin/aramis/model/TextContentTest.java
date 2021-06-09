package com.github.nsorin.aramis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextContentTest {

    @Test
    void constructTextFileWithContent() {
        String fileContent = "lorem ipsum";

        TextContent textContent = new TextContent(fileContent);

        assertEquals(fileContent, textContent.getText());
    }
}

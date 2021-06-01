package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class FileManagerDataTest {

    @Test
    void constructAndGetContentAndProperties() {
        TextContent textContent = new TextContent();
        FileProperties fileProperties = new FileProperties();
        FileManagerData response = new FileManagerData(textContent, fileProperties);

        assertSame(textContent, response.textContent());
        assertSame(fileProperties, response.fileProperties());

    }
}

package com.github.nsorin.aramis.axml;

import com.github.nsorin.aramis.model.TextContent;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AXMLReaderTest {

    @Test
    void readValidAXMLWithText() {
        AXMLReader reader = new AXMLReader();

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("test_simple.axml")).getFile();
        TextContent content = reader.readContent(new File(path));

        assertEquals("Some Text", content.getContent());
    }

    @Test
    void readInvalidAXML_throwsException() {
        AXMLReader reader = new AXMLReader();

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("test_invalid.axml")).getFile();
        assertThrows(
                InvalidAXMLException.class,
                () -> reader.readContent(new File(path))
        );
    }

    @Test
    void readPlainText_throwsException() {
        AXMLReader reader = new AXMLReader();

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("test_invalid.axml")).getFile();
        assertThrows(
                InvalidAXMLException.class,
                () -> reader.readContent(new File(path))
        );
    }
}
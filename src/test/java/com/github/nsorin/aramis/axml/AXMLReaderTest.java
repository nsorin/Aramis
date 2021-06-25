package com.github.nsorin.aramis.axml;

import com.github.nsorin.aramis.utils.TestFileUtils;
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
        AXMLContent content = reader.readContent(new File(path));

        assertEquals("Some Text", content.text());
    }

    @Test
    void readValidAXMLWithTitle() {
        AXMLReader reader = new AXMLReader();

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("test_title_author.axml")).getFile();
        AXMLContent content = reader.readContent(new File(path));

        assertEquals("Some Title", content.title());
    }

    @Test
    void readValidAXMLWithAuthor() {
        AXMLReader reader = new AXMLReader();

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("test_title_author.axml")).getFile();
        AXMLContent content = reader.readContent(new File(path));

        assertEquals("Some Author", content.author());
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

    @Test
    void readNonExistingFile_throwsException() {
        AXMLReader reader = new AXMLReader();

        assertThrows(
                InvalidAXMLException.class,
                () -> reader.readContent(TestFileUtils.createNonExistingTempFile())
        );
    }
}

package com.github.nsorin.aramis.axml;

import com.github.nsorin.aramis.utils.TestFileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AXMLWriterTest {

    @Test
    void writeAXMLWithText() {
        File file = TestFileUtils.createNonExistingTempFile();
        AXMLWriter writer = new AXMLWriter();

        AXMLContent content = new AXMLContent("Some Text", null, null);
        writer.writeContent(file, content);

        AXMLReader reader = new AXMLReader();
        assertEquals(content.text(), reader.readContent(file).text());
        file.delete();
    }

    @Test
    void overwriteAXMLWithText() {
        File file = TestFileUtils.createNonExistingTempFile();
        AXMLWriter writer = new AXMLWriter();
        AXMLContent oldContent = new AXMLContent("Some Long Text", null, null);
        writer.writeContent(file, oldContent);

        AXMLContent newContent = new AXMLContent("Some Text", null, null);
        writer.writeContent(file, newContent);

        AXMLReader reader = new AXMLReader();
        assertEquals(newContent.text(), reader.readContent(file).text());
        file.delete();
    }

    @Test
    void writeAXMLWithTextTitleAndAuthor() {
        File file = TestFileUtils.createNonExistingTempFile();
        AXMLWriter writer = new AXMLWriter();

        AXMLContent content = new AXMLContent("Some Text", "Some Title", "Some Author");
        writer.writeContent(file, content);

        AXMLReader reader = new AXMLReader();
        assertEquals(content.text(), reader.readContent(file).text());
        assertEquals(content.title(), reader.readContent(file).title());
        assertEquals(content.author(), reader.readContent(file).author());
        file.delete();
    }
}

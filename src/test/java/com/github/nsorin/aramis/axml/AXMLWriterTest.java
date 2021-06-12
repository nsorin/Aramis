package com.github.nsorin.aramis.axml;

import com.github.nsorin.aramis.utils.TestFileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AXMLWriterTest {

    @Test
    void writeAXMLWithText() throws IOException {
        File file = TestFileUtils.createNonExistingTempFile();
        AXMLWriter writer = new AXMLWriter();

        AXMLContent content = new AXMLContent("Some Text");
        writer.writeContent(file, content);

        System.out.println(String.join("\n", Files.readAllLines(file.toPath())));

        AXMLReader reader = new AXMLReader();
        assertEquals(content.text(), reader.readContent(file).text());
        file.delete();
    }
}

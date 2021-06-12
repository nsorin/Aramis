package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.axml.AXMLReader;
import com.github.nsorin.aramis.axml.AXMLWriter;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.github.nsorin.aramis.utils.TestFileUtils.createExistingTempFile;
import static com.github.nsorin.aramis.utils.TestFileUtils.createNonExistingTempFile;
import static org.junit.jupiter.api.Assertions.*;

class FileManagerFileSystemTest {

    private FileManager fileManager;

    @BeforeEach
    void setUp() {
        fileManager = new FileManagerFilesystem(new AXMLReader(), new AXMLWriter());
    }

    @Test
    void loadPlainTextFile() throws IOException {
        File fileToLoad = createExistingTempFile("Hello World!");

        FileManagerData fileManagerData = fileManager.loadFile(fileToLoad);

        assertFalse(fileManagerData.fileProperties().isAXML());
        assertEquals("Hello World!", fileManagerData.textContent().getText());
        assertEquals(fileToLoad.getName(), fileManagerData.fileProperties().getName());
        assertEquals(fileToLoad.getAbsolutePath(), fileManagerData.fileProperties().getLocation());
    }

    @Test
    void saveFile() throws IOException {
        File fileToLoad = createExistingTempFile("Hello World!");
        FileManagerData data = fileManager.loadFile(fileToLoad);

        data.textContent().setText("Hello Universe!");
        fileManager.saveFile(data);

        FileManagerData resultingData = fileManager.loadFile(fileToLoad);
        assertEquals(data.textContent().getText(), resultingData.textContent().getText());
    }

    @Test
    void saveToFileOverwrite() throws IOException {
        File targetFile = createExistingTempFile("Hello Universe!");
        TextContent textContent = new TextContent("Hello World!");
        FileProperties fileProperties = new FileProperties(null, null);

        FileManagerData savedData = fileManager.saveToFile(new FileManagerData(textContent, fileProperties), targetFile);
        FileManagerData reloadedData = fileManager.loadFile(targetFile);

        assertEquals(textContent.getText(), reloadedData.textContent().getText());
        assertEquals(textContent.getText(), savedData.textContent().getText());
        assertEquals(targetFile.getAbsolutePath(), savedData.fileProperties().getLocation());
        assertEquals(targetFile.getName(), savedData.fileProperties().getName());
    }

    @Test
    void saveToFileNew() throws IOException {
        File targetFile = createNonExistingTempFile();
        TextContent textContent = new TextContent("Hello World!");
        FileProperties fileProperties = new FileProperties(null, null);

        FileManagerData savedData = fileManager.saveToFile(new FileManagerData(textContent, fileProperties), targetFile);
        FileManagerData reloadedData = fileManager.loadFile(targetFile);

        assertEquals(textContent.getText(), reloadedData.textContent().getText());
        assertEquals(textContent.getText(), savedData.textContent().getText());
        assertEquals(targetFile.getAbsolutePath(), savedData.fileProperties().getLocation());
        assertEquals(targetFile.getName(), savedData.fileProperties().getName());
    }

    @Test
    void loadAXMLFile() throws IOException {
        File fileToLoad = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test_simple.axml")).getFile());

        FileManagerData fileManagerData = fileManager.loadFile(fileToLoad);

        assertTrue(fileManagerData.fileProperties().isAXML());
        assertEquals("Some Text", fileManagerData.textContent().getText());
        assertEquals(fileToLoad.getName(), fileManagerData.fileProperties().getName());
        assertEquals(fileToLoad.getAbsolutePath(), fileManagerData.fileProperties().getLocation());
    }

    @Test
    void saveNewAXMLFile() throws IOException {
        File targetFile = createNonExistingTempFile();
        TextContent textContent = new TextContent("Hello World!");
        FileProperties fileProperties = new FileProperties(null, null, true);

        FileManagerData savedData = fileManager.saveToFile(new FileManagerData(textContent, fileProperties), targetFile);
        FileManagerData reloadedData = fileManager.loadFile(targetFile);

        assertEquals(textContent.getText(), reloadedData.textContent().getText());
        assertEquals(textContent.getText(), savedData.textContent().getText());
        assertTrue(savedData.fileProperties().isAXML());
        assertEquals(targetFile.getAbsolutePath(), savedData.fileProperties().getLocation());
        assertEquals(targetFile.getName(), savedData.fileProperties().getName());
    }

    @Test
    void saveExistingAXMLFile() throws IOException {
        File fileToLoad = createExistingTempFile("<axml><text>Hello World!</text></axml>");
        FileManagerData data = fileManager.loadFile(fileToLoad);

        data.textContent().setText("Hello Universe!");
        fileManager.saveFile(data);

        FileManagerData resultingData = fileManager.loadFile(fileToLoad);
        assertEquals(data.textContent().getText(), resultingData.textContent().getText());
    }

}

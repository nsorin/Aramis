package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.github.nsorin.aramis.utils.TestFileUtils.createExistingTempFile;
import static com.github.nsorin.aramis.utils.TestFileUtils.createNonExistingTempFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerFileSystemTest {

    @Test
    void loadFile() throws IOException {
        File fileToLoad = createExistingTempFile("Hello World!");

        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        FileManagerData fileManagerData = fileManager.loadFile(fileToLoad);

        assertEquals("Hello World!", fileManagerData.textContent().getContent());
        assertEquals(fileToLoad.getName(), fileManagerData.fileProperties().getName());
        assertEquals(fileToLoad.getAbsolutePath(), fileManagerData.fileProperties().getLocation());
    }

    @Test
    void saveFile() throws IOException {
        File fileToLoad = createExistingTempFile("Hello World!");
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        FileManagerData data = fileManager.loadFile(fileToLoad);

        data.textContent().setContent("Hello Universe!");
        fileManager.saveFile(data);

        FileManagerData resultingData = fileManager.loadFile(fileToLoad);
        assertEquals(data.textContent().getContent(), resultingData.textContent().getContent());
    }

    @Test
    void saveToFileOverwrite() throws IOException {
        File targetFile = createExistingTempFile("Hello Universe!");
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        TextContent textContent = new TextContent("Hello World!");
        FileProperties fileProperties = new FileProperties(null, null);

        FileManagerData savedData = fileManager.saveToFile(new FileManagerData(textContent, fileProperties), targetFile);
        FileManagerData reloadedData = fileManager.loadFile(targetFile);

        assertEquals(textContent.getContent(), reloadedData.textContent().getContent());
        assertEquals(textContent.getContent(), savedData.textContent().getContent());
        assertEquals(targetFile.getAbsolutePath(), savedData.fileProperties().getLocation());
        assertEquals(targetFile.getName(), savedData.fileProperties().getName());
    }

    @Test
    void saveToFileNew() throws IOException {
        File targetFile = createNonExistingTempFile();
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        TextContent textContent = new TextContent("Hello World!");
        FileProperties fileProperties = new FileProperties(null, null);

        FileManagerData savedData = fileManager.saveToFile(new FileManagerData(textContent, fileProperties), targetFile);
        FileManagerData reloadedData = fileManager.loadFile(targetFile);

        assertEquals(textContent.getContent(), reloadedData.textContent().getContent());
        assertEquals(textContent.getContent(), savedData.textContent().getContent());
        assertEquals(targetFile.getAbsolutePath(), savedData.fileProperties().getLocation());
        assertEquals(targetFile.getName(), savedData.fileProperties().getName());
    }

}

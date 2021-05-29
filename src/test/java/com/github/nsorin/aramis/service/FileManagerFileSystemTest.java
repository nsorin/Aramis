package com.github.nsorin.aramis.service;

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
        TextContent textContent = fileManager.loadFile(fileToLoad);

        assertEquals("Hello World!", textContent.getContent());
        assertEquals(fileToLoad.getName(), textContent.getFileName());
        assertEquals(fileToLoad.getAbsolutePath(), textContent.getFileLocation());
    }

    @Test
    void saveFile() throws IOException {
        File fileToLoad = createExistingTempFile("Hello World!");
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        TextContent textContentToSave = fileManager.loadFile(fileToLoad);

        textContentToSave.setContent("Hello Universe!");
        fileManager.saveFile(textContentToSave);

        TextContent resultingFile = fileManager.loadFile(fileToLoad);
        assertEquals(textContentToSave.getContent(), resultingFile.getContent());
    }

    @Test
    void saveToFileOverwrite() throws IOException {
        File targetFile = createExistingTempFile("Hello Universe!");
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        TextContent textContentToSave = new TextContent(null, null, "Hello World!");

        TextContent savedTextContent = fileManager.saveToFile(textContentToSave, targetFile);
        TextContent reloadedFile = fileManager.loadFile(targetFile);

        assertEquals(textContentToSave.getContent(), reloadedFile.getContent());
        assertEquals(textContentToSave.getContent(), savedTextContent.getContent());
        assertEquals(targetFile.getAbsolutePath(), savedTextContent.getFileLocation());
        assertEquals(targetFile.getName(), savedTextContent.getFileName());
    }

    @Test
    void saveToFileNew() throws IOException {
        File targetFile = createNonExistingTempFile();
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        TextContent textContentToSave = new TextContent(null, null, "Hello World!");

        TextContent savedTextContent = fileManager.saveToFile(textContentToSave, targetFile);
        TextContent reloadedFile = fileManager.loadFile(targetFile);

        assertEquals(textContentToSave.getContent(), reloadedFile.getContent());
        assertEquals(textContentToSave.getContent(), savedTextContent.getContent());
        assertEquals(targetFile.getAbsolutePath(), savedTextContent.getFileLocation());
        assertEquals(targetFile.getName(), savedTextContent.getFileName());
    }

}

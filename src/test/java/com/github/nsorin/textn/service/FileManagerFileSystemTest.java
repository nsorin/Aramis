package com.github.nsorin.textn.service;

import com.github.nsorin.textn.model.Text;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerFileSystemTest {

    @Test
    void loadFile() throws IOException {
        File fileToLoad = createTempFile("test", ".txt", "Hello World!");

        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        Text text = fileManager.loadFile(fileToLoad);

        assertEquals("Hello World!", text.getContent());
        assertEquals(fileToLoad.getName(), text.getFileName());
        assertEquals(fileToLoad.getAbsolutePath(), text.getFileLocation());
    }

    @Test
    void saveFile() throws IOException {
        File fileToLoad = createTempFile("test", ".txt", "Hello World!");
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        Text textToSave = fileManager.loadFile(fileToLoad);

        textToSave.setContent("Hello Universe!");
        fileManager.saveFile(textToSave);

        Text resultingFile = fileManager.loadFile(fileToLoad);
        assertEquals(textToSave.getContent(), resultingFile.getContent());
    }

    @Test
    void saveToFileOverwrite() throws IOException {
        File targetFile = createTempFile("test", ".txt", "Hello Universe!");
        FileManagerFilesystem fileManager = new FileManagerFilesystem();
        Text textToSave = new Text(null, null, "Hello World!");

        Text savedText = fileManager.saveToFile(textToSave, targetFile);
        Text reloadedFile = fileManager.loadFile(targetFile);

        assertEquals(textToSave.getContent(), reloadedFile.getContent());
        assertEquals(textToSave.getContent(), savedText.getContent());
        assertEquals(targetFile.getAbsolutePath(), savedText.getFileLocation());
        assertEquals(targetFile.getName(), savedText.getFileName());
    }

    private File createTempFile(String prefix, String suffix, String content) throws IOException {
        Path path = Files.createTempFile(prefix, suffix);
        Files.writeString(path, content);
        File file = path.toFile();
        file.deleteOnExit();
        return file;
    }
}

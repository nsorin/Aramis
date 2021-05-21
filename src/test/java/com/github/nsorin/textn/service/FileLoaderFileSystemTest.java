package com.github.nsorin.textn.service;

import com.github.nsorin.textn.model.TextFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileLoaderFileSystemTest {

    @Test
    void loadFile() throws IOException {
        String prefix = "test";
        String suffix = ".txt";
        String content = "Hello World!";
        File fileToLoad = createTempFile(prefix, suffix, content);

        FileLoaderFilesystem fileLoader = new FileLoaderFilesystem();
        TextFile textFile = fileLoader.loadFile(fileToLoad);

        assertEquals(content, textFile.getContent());
        assertEquals(fileToLoad.getName(), textFile.getName());
        assertEquals(fileToLoad.getAbsolutePath(), textFile.getLocation());
    }

    private File createTempFile(String prefix, String suffix, String content) throws IOException {
        Path path = Files.createTempFile(prefix, suffix);
        Files.writeString(path, content);
        File file = path.toFile();
        file.deleteOnExit();
        return file;
    }
}

package com.github.nsorin.textn.service;

import com.github.nsorin.textn.model.TextFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManagerFilesystem implements FileManager {
    public TextFile loadFile(File file) throws IOException {
        String content = String.join("\n", Files.readAllLines(file.toPath()));
        String name = file.getName();
        String location = file.getAbsolutePath();

        return new TextFile(location, name, content);
    }

    @Override
    public void saveFile(TextFile textFile) throws IOException {
        Files.writeString(Paths.get(textFile.getLocation()), textFile.getContent(), StandardOpenOption.WRITE);
    }
}

package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.TextContent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManagerFilesystem implements FileManager {

    public TextContent loadFile(File file) throws IOException {
        String content = String.join("\n", Files.readAllLines(file.toPath()));
        String name = file.getName();
        String location = file.getAbsolutePath();

        return new TextContent(location, name, content);
    }

    @Override
    public void saveFile(TextContent textContent) throws IOException {
        Files.writeString(Paths.get(textContent.getFileLocation()), textContent.getContent(), StandardOpenOption.WRITE);
    }

    @Override
    public TextContent saveToFile(TextContent textContent, File file) throws IOException {
        Files.writeString(file.toPath(), textContent.getContent(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return new TextContent(file.getAbsolutePath(), file.getName(), textContent.getContent());
    }
}

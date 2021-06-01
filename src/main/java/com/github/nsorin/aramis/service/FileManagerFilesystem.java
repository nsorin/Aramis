package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManagerFilesystem implements FileManager {

    @Override
    public FileManagerData loadFile(File file) throws IOException {
        String content = String.join("\n", Files.readAllLines(file.toPath()));
        String name = file.getName();
        String location = file.getAbsolutePath();

        return new FileManagerData(
                new TextContent(content),
                new FileProperties(location, name)
        );
    }

    @Override
    public void saveFile(FileManagerData data) throws IOException {
        Files.writeString(Paths.get(data.fileProperties().getLocation()), data.textContent().getContent(), StandardOpenOption.WRITE);
    }

    @Override
    public FileManagerData saveToFile(FileManagerData data, File file) throws IOException {
        Files.writeString(file.toPath(), data.textContent().getContent(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return new FileManagerData(
                new TextContent(data.textContent().getContent()),
                new FileProperties(file.getAbsolutePath(), file.getName())
        );
    }
}

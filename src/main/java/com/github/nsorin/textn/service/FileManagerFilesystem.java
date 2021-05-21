package com.github.nsorin.textn.service;

import com.github.nsorin.textn.model.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManagerFilesystem implements FileManager {
    public Text loadFile(File file) throws IOException {
        String content = String.join("\n", Files.readAllLines(file.toPath()));
        String name = file.getName();
        String location = file.getAbsolutePath();

        return new Text(location, name, content);
    }

    @Override
    public void saveFile(Text text) throws IOException {
        Files.writeString(Paths.get(text.getFileLocation()), text.getContent(), StandardOpenOption.WRITE);
    }

    @Override
    public Text saveToFile(Text text, File file) throws IOException {
        Files.writeString(file.toPath(), text.getContent(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return new Text(file.getAbsolutePath(), file.getName(), text.getContent());
    }
}

package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.axml.AXMLContent;
import com.github.nsorin.aramis.axml.AXMLReader;
import com.github.nsorin.aramis.axml.AXMLWriter;
import com.github.nsorin.aramis.axml.InvalidAXMLException;
import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManagerFilesystem implements FileManager {

    private final AXMLReader axmlReader;
    private final AXMLWriter axmlWriter;

    @Injectable
    public FileManagerFilesystem(AXMLReader axmlReader, AXMLWriter axmlWriter) {
        this.axmlReader = axmlReader;
        this.axmlWriter = axmlWriter;
    }

    @Override
    public FileManagerData loadFile(File file) throws IOException {
        String name = file.getName();
        String location = file.getAbsolutePath();

        String content;
        boolean isAXML = false;

        try {
            content = loadAXML(file);
            isAXML = true;
        } catch (InvalidAXMLException e) {
            content = loadPlainText(file);
        }

        return new FileManagerData(
                new TextContent(content),
                new FileProperties(location, name, isAXML)
        );
    }

    private String loadAXML(File file) {
        return axmlReader.readContent(file).text();
    }

    private String loadPlainText(File file) throws IOException {
        return String.join("\n", Files.readAllLines(file.toPath()));
    }

    @Override
    public void saveFile(FileManagerData data) throws IOException {
        if (data.fileProperties().isAXML()) {
            axmlWriter.writeContent(
                    new File(data.fileProperties().getLocation()),
                    new AXMLContent(data.textContent().getText())
            );
        } else {
            Files.writeString(Paths.get(data.fileProperties().getLocation()), data.textContent().getText(), StandardOpenOption.WRITE);
        }
    }

    @Override
    public FileManagerData saveToFile(FileManagerData data, File file) throws IOException {
        if (data.fileProperties().isAXML()) {
            axmlWriter.writeContent(file, new AXMLContent(data.textContent().getText()));
        } else {
            Files.writeString(file.toPath(), data.textContent().getText(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        return new FileManagerData(
                new TextContent(data.textContent().getText()),
                new FileProperties(file.getAbsolutePath(), file.getName(), data.fileProperties().isAXML())
        );
    }
}

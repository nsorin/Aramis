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

        TextContent content;
        boolean isAXML = false;

        try {
            content = loadAXML(file);
            isAXML = true;
        } catch (InvalidAXMLException e) {
            content = loadPlainText(file);
        }

        return new FileManagerData(
                content,
                new FileProperties(location, name, isAXML)
        );
    }

    private TextContent loadAXML(File file) {
        AXMLContent axmlContent = axmlReader.readContent(file);
        TextContent textContent = new TextContent(axmlContent.text());
        textContent.setTitle(axmlContent.title());
        textContent.setAuthor(axmlContent.author());
        return textContent;
    }

    private TextContent loadPlainText(File file) throws IOException {
        return new TextContent(String.join("\n", Files.readAllLines(file.toPath())));
    }

    @Override
    public void saveFile(FileManagerData data) throws IOException {
        if (data.fileProperties().isAXML()) {
            axmlWriter.writeContent(
                    new File(data.fileProperties().getLocation()),
                    new AXMLContent(
                            data.textContent().getText(),
                            data.textContent().getTitle(),
                            data.textContent().getAuthor()
                    )
            );
        } else {
            Files.writeString(Paths.get(data.fileProperties().getLocation()), data.textContent().getText(), StandardOpenOption.WRITE);
        }
    }

    @Override
    public FileManagerData saveToFile(FileManagerData data, File file) throws IOException {
        if (data.fileProperties().isAXML()) {
            axmlWriter.writeContent(file, new AXMLContent(
                    data.textContent().getText(),
                    data.textContent().getTitle(),
                    data.textContent().getAuthor()
            ));
        } else {
            Files.writeString(file.toPath(), data.textContent().getText(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        return new FileManagerData(
                data.textContent(),
                new FileProperties(file.getAbsolutePath(), file.getName(), data.fileProperties().isAXML())
        );
    }
}

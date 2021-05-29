package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.TextContent;

import java.io.File;
import java.io.IOException;

public interface FileManager {
    TextContent loadFile(File file) throws IOException;

    void saveFile(TextContent fileToSave) throws IOException;

    TextContent saveToFile(TextContent fileToSave, File file) throws IOException;
}

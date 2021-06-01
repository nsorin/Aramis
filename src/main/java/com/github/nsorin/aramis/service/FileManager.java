package com.github.nsorin.aramis.service;

import java.io.File;
import java.io.IOException;

public interface FileManager {
    FileManagerData loadFile(File file) throws IOException;

    void saveFile(FileManagerData data) throws IOException;

    FileManagerData saveToFile(FileManagerData data, File file) throws IOException;
}

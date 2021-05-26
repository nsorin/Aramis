package com.github.nsorin.aramis.service;

import com.github.nsorin.aramis.model.Text;

import java.io.File;
import java.io.IOException;

public interface FileManager {
    Text loadFile(File file) throws IOException;

    void saveFile(Text fileToSave) throws IOException;

    Text saveToFile(Text fileToSave, File file) throws IOException;
}

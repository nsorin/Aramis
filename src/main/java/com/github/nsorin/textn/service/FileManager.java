package com.github.nsorin.textn.service;

import com.github.nsorin.textn.model.Text;

import java.io.File;
import java.io.IOException;

public interface FileManager {
    Text loadFile(File file) throws IOException;

    void saveFile(Text fileToSave) throws IOException;

    Text saveToFile(Text fileToSave, File file) throws IOException;
}

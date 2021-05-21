package com.github.nsorin.textn.service;

import com.github.nsorin.textn.model.TextFile;

import java.io.File;
import java.io.IOException;

public interface FileLoader {
    TextFile loadFile(File file) throws IOException;
}

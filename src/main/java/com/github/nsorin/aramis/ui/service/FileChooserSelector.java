package com.github.nsorin.aramis.ui.service;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class FileChooserSelector implements FileSelector {

    public static final String OPEN_FILE_HEADER = "Open text file";
    public static final String SAVE_FILE_HEADER = "Open text file";

    @Override
    public File selectFileToOpen(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(OPEN_FILE_HEADER);
        return fileChooser.showOpenDialog(window);
    }

    @Override
    public File selectFileToSave(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SAVE_FILE_HEADER);
        return fileChooser.showSaveDialog(window);
    }
}

package com.github.nsorin.aramis.ui.service;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class FileChooserSelector implements FileSelector {

    public static final String OPEN_FILE_HEADER = "Open File";
    public static final String SAVE_FILE_HEADER = "Save File";
    public static final String AXML_FILTER_NAME = "AXML Files";
    public static final String AXML_FILTER_MATCHER = "*.axml";
    public static final String ALL_FILTER_NAME = "All Files";
    public static final String ALL_FILTER_MATCHER = "*";

    @Override
    public File selectFileToOpen(Window window) {
        return getFileChooser(OPEN_FILE_HEADER).showOpenDialog(window);
    }

    @Override
    public File selectFileToSave(Window window) {
        return getFileChooser(SAVE_FILE_HEADER).showSaveDialog(window);
    }

    private FileChooser getFileChooser(String header) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(header);
        FileChooser.ExtensionFilter axmlFilter = new FileChooser.ExtensionFilter(AXML_FILTER_NAME, AXML_FILTER_MATCHER);
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter(ALL_FILTER_NAME, ALL_FILTER_MATCHER);
        fileChooser.getExtensionFilters().add(axmlFilter);
        fileChooser.getExtensionFilters().add(allFilter);
        return fileChooser;
    }
}

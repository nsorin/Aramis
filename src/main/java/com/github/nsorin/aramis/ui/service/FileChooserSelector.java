package com.github.nsorin.aramis.ui.service;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class FileChooserSelector implements FileSelector {

    private static final String OPEN_FILE_HEADER = "Open File";
    private static final String SAVE_FILE_HEADER = "Save File";
    private static final String AXML_FILTER_NAME = "AXML Files";
    private static final String AXML_EXTENSION = ".axml";
    private static final String AXML_FILTER_MATCHER = "*" + AXML_EXTENSION;
    private static final String ALL_FILTER_NAME = "All Files";
    private static final String ALL_FILTER_MATCHER = "*";

    @Override
    public File selectFileToOpen(Window window) {
        return getFileChooser(OPEN_FILE_HEADER).showOpenDialog(window);
    }

    @Override
    public File selectFileToSave(Window window, boolean axml) {
        File file = getFileChooser(SAVE_FILE_HEADER).showSaveDialog(window);
        return getFinalSavedFile(axml, file);
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

    private File getFinalSavedFile(boolean axml, File file) {
        if (axml && !file.getName().endsWith(AXML_EXTENSION)) {
            return new File(file.getAbsolutePath() + AXML_EXTENSION);
        }
        return file;
    }
}

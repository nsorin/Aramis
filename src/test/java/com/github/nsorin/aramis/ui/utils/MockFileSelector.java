package com.github.nsorin.aramis.ui.utils;

import com.github.nsorin.aramis.ui.service.FileSelector;
import com.github.nsorin.aramis.utils.TestFileUtils;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class MockFileSelector implements FileSelector {

    public static final String TEMP_FILE_CONTENT = "Hello World!";

    @Override
    public File selectFileToOpen(Window window) {
        try {
            return TestFileUtils.createExistingTempFile(TEMP_FILE_CONTENT);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public File selectFileToSave(Window window) {
        return TestFileUtils.createNonExistingTempFile();
    }
}

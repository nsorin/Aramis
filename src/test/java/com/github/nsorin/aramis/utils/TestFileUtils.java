package com.github.nsorin.aramis.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFileUtils {

    public static final String EXISTING_FILE_NAME = "test-exists.txt";
    public static final String NON_EXISTING_FILE_NAME = "test.txt";
    public static final String EXISTING_FILE_PATH = "/tmp/" + EXISTING_FILE_NAME;
    public static final String NON_EXISTING_FILE_PATH = "/tmp/" + NON_EXISTING_FILE_NAME;

    public static File createExistingTempFile(String content) throws IOException {
        File file = new File(EXISTING_FILE_PATH);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();
        Files.writeString(Path.of(file.getAbsolutePath()), content);
        return file;
    }

    public static File createNonExistingTempFile() {
        File targetFile = new File(NON_EXISTING_FILE_PATH);
        targetFile.deleteOnExit();
        return targetFile;
    }
}

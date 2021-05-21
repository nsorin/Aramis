package com.github.nsorin.textn.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFileUtils {

    public static final String EXISTING_FILE_PATH = "/tmp/test-exists.txt";
    public static final String NON_EXISTING_FILE_PATH = "/tmp/test.txt";

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

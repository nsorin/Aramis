package com.github.nsorin.aramis.ui.service;

import javafx.stage.Window;

import java.io.File;

public interface FileSelector {
    File selectFileToOpen(Window window);

    File selectFileToSave(Window window, boolean axml);
}

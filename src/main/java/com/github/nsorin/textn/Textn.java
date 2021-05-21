package com.github.nsorin.textn;

import com.github.nsorin.textn.injection.DependencyProvider;
import com.github.nsorin.textn.service.FileManager;
import com.github.nsorin.textn.service.FileManagerFilesystem;
import com.github.nsorin.textn.ui.TextnApplication;
import com.github.nsorin.textn.ui.service.FileChooserSelector;
import com.github.nsorin.textn.ui.service.FileSelector;
import javafx.application.Application;

public class Textn {
    public static void main(String[] args) {
        provideDependencies();
        Application.launch(TextnApplication.class, args);
    }

    public static void provideDependencies() {
        DependencyProvider.getProvider().provide(FileManager.class, FileManagerFilesystem.class);
        DependencyProvider.getProvider().provide(FileSelector.class, FileChooserSelector.class);
    }
}

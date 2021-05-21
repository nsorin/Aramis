package com.github.nsorin.textn;

import com.github.nsorin.textn.injection.DependencyProvider;
import com.github.nsorin.textn.service.FileLoader;
import com.github.nsorin.textn.service.FileLoaderFilesystem;
import com.github.nsorin.textn.ui.TextnApplication;
import javafx.application.Application;

public class Textn {
    public static void main(String[] args) {
        provideDependencies();
        Application.launch(TextnApplication.class, args);
    }

    public static void provideDependencies() {
        DependencyProvider.getProvider().provide(FileLoader.class, FileLoaderFilesystem.class);
    }
}

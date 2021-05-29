package com.github.nsorin.aramis;

import com.github.nsorin.aramis.injector.DependencyProvider;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.service.FileManagerFilesystem;
import com.github.nsorin.aramis.ui.TextEditorApplication;
import com.github.nsorin.aramis.ui.service.FileChooserSelector;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.application.Application;

public class TextEditor {
    public static void main(String[] args) {
        provideDependencies();
        Application.launch(TextEditorApplication.class, args);
    }

    public static void provideDependencies() {
        DependencyProvider.getProvider().provide(FileManager.class, FileManagerFilesystem.class);
        DependencyProvider.getProvider().provide(FileSelector.class, FileChooserSelector.class);
        DependencyProvider.getProvider().provideSingleton(ApplicationState.class);
    }
}

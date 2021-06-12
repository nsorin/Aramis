package com.github.nsorin.aramis;

import com.github.nsorin.aramis.axml.AXMLReader;
import com.github.nsorin.aramis.axml.AXMLWriter;
import com.github.nsorin.aramis.injector.DependencyProvider;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.observer.EventObserver;
import com.github.nsorin.aramis.observer.EventObserverInterface;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.service.FileManagerFilesystem;
import com.github.nsorin.aramis.ui.TextEditorApplication;
import com.github.nsorin.aramis.ui.command.FileCommand;
import com.github.nsorin.aramis.ui.service.*;
import javafx.application.Application;

public class TextEditor {
    public static void main(String[] args) {
        provideDependencies();
        Application.launch(TextEditorApplication.class, args);
    }

    public static void provideDependencies() {
        DependencyProvider.getProvider().provide(FileCommand.class);
        DependencyProvider.getProvider().provide(FileManager.class, FileManagerFilesystem.class);
        DependencyProvider.getProvider().provide(FileSelector.class, FileChooserSelector.class);

        DependencyProvider.getProvider().provide(AlertService.class, AlertDispatcher.class);
        DependencyProvider.getProvider().provide(ConfirmService.class, ConfirmDialogDispatcher.class);

        DependencyProvider.getProvider().provide(AXMLReader.class);
        DependencyProvider.getProvider().provide(AXMLWriter.class);

        DependencyProvider.getProvider().provideSingleton(ApplicationState.class);

        DependencyProvider.getProvider().provideInstance(EventObserverInterface.class, EventObserver.getObserver());
    }
}

package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class FileCommand {
    private final ApplicationState applicationState;
    private final FileSelector fileSelector;
    private final FileManager fileManager;

    @Injectable
    public FileCommand(ApplicationState applicationState, FileSelector fileSelector, FileManager fileManager) {
        this.applicationState = applicationState;
        this.fileSelector = fileSelector;
        this.fileManager = fileManager;
    }

    public void newFile() {
        this.applicationState.setTextContent(new TextContent());
        this.applicationState.setFileProperties(new FileProperties());
        this.applicationState.setSaved(false);
    }

    public void openFile(Window window) {
        File file = fileSelector.selectFileToOpen(window);
        try {
            applicationState.setTextContent(fileManager.loadFile(file));
            applicationState.setFileProperties(new FileProperties(
                    applicationState.getTextContent().getFileLocation(),
                    applicationState.getTextContent().getFileName()
            ));
            applicationState.setSaved(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

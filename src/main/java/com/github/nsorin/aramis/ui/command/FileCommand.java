package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.service.FileManagerData;
import com.github.nsorin.aramis.ui.service.AlertService;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class FileCommand {
    private final ApplicationState applicationState;
    private final FileSelector fileSelector;
    private final FileManager fileManager;
    private final AlertService alertService;

    @Injectable
    public FileCommand(ApplicationState applicationState,
                       FileSelector fileSelector,
                       FileManager fileManager,
                       AlertService alertService) {
        this.applicationState = applicationState;
        this.fileSelector = fileSelector;
        this.fileManager = fileManager;
        this.alertService = alertService;
    }

    public void newFile() {
        this.applicationState.setTextContent(new TextContent());
        this.applicationState.setFileProperties(new FileProperties());
        this.applicationState.setSaved(false);
    }

    public void openFile(Window window) {
        File file = fileSelector.selectFileToOpen(window);
        if (file != null) {
            loadFile(file);
        }
    }

    public void saveFile(Window window) {
        if (applicationState.getFileProperties().isNew()) {
            saveFileAs(window);
        } else {
            saveExistingFile();
        }
    }

    public void saveFileAs(Window window) {
        File file = fileSelector.selectFileToSave(window);
        if (file != null) {
            try {
                FileManagerData savedData = fileManager.saveToFile(
                        new FileManagerData(applicationState.getTextContent(), applicationState.getFileProperties()),
                        file
                );
                applicationState.setTextContent(savedData.textContent());
                applicationState.setFileProperties(savedData.fileProperties());
                applicationState.setSaved(true);
            } catch (IOException e) {
                alertService.showError("Could not save file at location " + file.getAbsolutePath() + ".");
            }
        }
    }

    private void saveExistingFile() {
        try {
            fileManager.saveFile(
                    new FileManagerData(applicationState.getTextContent(), applicationState.getFileProperties())
            );
            applicationState.setSaved(true);
        } catch (IOException e) {
            alertService.showError("Could not save file at location " + applicationState.getFileProperties().getLocation() + ".");
        }
    }

    public void reloadFile() {
        File file = new File(applicationState.getFileProperties().getLocation());
        loadFile(file);
    }

    private void loadFile(File file) {
        try {
            FileManagerData loadedData = fileManager.loadFile(file);
            applicationState.setTextContent(loadedData.textContent());
            applicationState.setFileProperties(loadedData.fileProperties());
            applicationState.setSaved(true);
        } catch (IOException e) {
            alertService.showError("Could not load file at location " + file.getAbsolutePath() + ".");
        }
    }
}

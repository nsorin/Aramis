package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.service.FileManagerData;
import com.github.nsorin.aramis.ui.service.AlertService;
import com.github.nsorin.aramis.ui.service.ConfirmService;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class FileCommand {
    public static final String UNSAVED_CONFIRM_TITLE = "Current file is not saved";
    public static final String UNSAVED_CONFIRM_CONTENT = "Current file is not saved. Do you want so save it before proceeding?";
    private final ApplicationState applicationState;
    private final FileSelector fileSelector;
    private final FileManager fileManager;
    private final AlertService alertService;
    private final ConfirmService confirmService;

    @Injectable
    public FileCommand(ApplicationState applicationState,
                       FileSelector fileSelector,
                       FileManager fileManager,
                       AlertService alertService, ConfirmService confirmService) {
        this.applicationState = applicationState;
        this.fileSelector = fileSelector;
        this.fileManager = fileManager;
        this.alertService = alertService;
        this.confirmService = confirmService;
    }

    public void newFile(Window window) {
        if (applicationState.canCloseSafely()) {
            resetTextContent();
        } else {
            confirmService.confirm(
                    UNSAVED_CONFIRM_TITLE,
                    UNSAVED_CONFIRM_CONTENT,
                    () -> {
                        saveFile(window);
                        resetTextContent();
                    },
                    this::resetTextContent
            );
        }
    }

    private void resetTextContent() {
        applicationState.setTextContent(new TextContent());
        applicationState.setFileProperties(new FileProperties());
        applicationState.setSaved(false);
    }

    public void openFile(Window window) {
        if (applicationState.canCloseSafely()) {
            browseAndLoadFile(window);
        } else {
            confirmService.confirm(
                    UNSAVED_CONFIRM_TITLE,
                    UNSAVED_CONFIRM_CONTENT,
                    () -> {
                        saveFile(window);
                        browseAndLoadFile(window);
                    },
                    () -> browseAndLoadFile(window)
            );
        }
    }

    private void browseAndLoadFile(Window window) {
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

package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.command.FileCommand;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    private Node rootNode;

    @FXML
    private InputAreaController inputController;

    @Injectable
    FileCommand fileCommand;

    @Injectable
    private ApplicationState applicationState;

    @Injectable
    private FileManager fileManager;

    @Injectable
    private FileSelector fileSelector;

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (KeyboardShortcuts.NEW.match(event)) {
            onNewButtonClick(event);
        } else if (KeyboardShortcuts.OPEN.match(event)) {
            onOpenButtonClick(event);
        } else if (KeyboardShortcuts.SAVE.match(event)) {
            onSaveButtonClick(event);
        } else if (KeyboardShortcuts.SAVE_AS.match(event)) {
            onSaveAsButtonClick(event);
        }
    }

    @FXML
    void onNewButtonClick(Event e) {
        fileCommand.newFile();
    }

    @FXML
    void onOpenButtonClick(Event e) {
        fileCommand.openFile(rootNode.getScene().getWindow());
    }

    @FXML
    void onSaveButtonClick(Event e) {
        if (applicationState.getTextContent().isNew()) {
            saveFileAs();
        } else {
            saveFile();
        }
    }

    @FXML
    void onSaveAsButtonClick(Event e) {
        saveFileAs();
    }

    private void saveFile() {
        setTextContentToInputArea();
        try {
            fileManager.saveFile(applicationState.getTextContent());
            applicationState.setSaved(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFileAs() {
        File file = fileSelector.selectFileToSave(rootNode.getScene().getWindow());
        if (file != null) {
            setTextContentToInputArea();
            try {
                applicationState.setTextContent(fileManager.saveToFile(applicationState.getTextContent(), file));
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

    private void setTextContentToInputArea() {
        applicationState.getTextContent().setContent(inputController.inputArea.getText());
    }
}

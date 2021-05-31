package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.command.FileCommand;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class MainController {

    @FXML
    private Node rootNode;

    @Injectable
    FileCommand fileCommand;

    @Injectable
    private ApplicationState applicationState;

    @Injectable
    private FileManager fileManager;

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
            fileCommand.saveFileAs(rootNode.getScene().getWindow());
        } else {
            saveFile();
        }
    }

    @FXML
    void onSaveAsButtonClick(Event e) {
        fileCommand.saveFileAs(rootNode.getScene().getWindow());
    }

    private void saveFile() {
        try {
            fileManager.saveFile(applicationState.getTextContent());
            applicationState.setSaved(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

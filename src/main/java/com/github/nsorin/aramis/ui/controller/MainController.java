package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.ui.command.FileCommand;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

public class MainController {

    @FXML
    private Node rootNode;

    @Injectable
    FileCommand fileCommand;

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
        fileCommand.saveFile(rootNode.getScene().getWindow());
    }

    @FXML
    void onSaveAsButtonClick(Event e) {
        fileCommand.saveFileAs(rootNode.getScene().getWindow());
    }
}

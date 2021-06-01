package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.ui.command.FileCommand;
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
            fileCommand.newFile();
        } else if (KeyboardShortcuts.OPEN.match(event)) {
            fileCommand.openFile(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.SAVE.match(event)) {
            fileCommand.saveFile(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.SAVE_AS.match(event)) {
            fileCommand.saveFileAs(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.RELOAD.match(event)) {
            fileCommand.reloadFile();
        }
    }

}

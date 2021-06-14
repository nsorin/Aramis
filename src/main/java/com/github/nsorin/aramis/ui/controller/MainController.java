package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.event.DisplayConfigurationUpdated;
import com.github.nsorin.aramis.observer.OnEvent;
import com.github.nsorin.aramis.ui.command.DisplayCommand;
import com.github.nsorin.aramis.ui.command.FileCommand;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Node rootNode;

    @Injectable
    private FileCommand fileCommand;

    @Injectable
    private DisplayCommand displayCommand;

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (KeyboardShortcuts.NEW.match(event)) {
            fileCommand.newFile(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.OPEN.match(event)) {
            fileCommand.openFile(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.SAVE.match(event)) {
            fileCommand.saveFile(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.SAVE_AS.match(event)) {
            fileCommand.saveFileAs(rootNode.getScene().getWindow());
        } else if (KeyboardShortcuts.RELOAD.match(event)) {
            fileCommand.reloadFile();
        } else if (KeyboardShortcuts.FULL_SCREEN.match(event)) {
            displayCommand.toggleFullScreen();
        } else if (KeyboardShortcuts.ZOOM_IN.match(event) || KeyboardShortcuts.ZOOM_IN_NUMPAD.match(event)) {
            displayCommand.zoomIn();
        } else if (KeyboardShortcuts.ZOOM_OUT.match(event) || KeyboardShortcuts.ZOOM_OUT_NUMPAD.match(event)) {
            displayCommand.zoomOut();
        }
    }

    @OnEvent
    public void onDisplayConfigurationUpdated(DisplayConfigurationUpdated event) {
        getStage().setFullScreen(event.displayConfiguration().isFullScreen());
    }

    private Stage getStage() {
        return (Stage) rootNode.getScene().getWindow();
    }

}

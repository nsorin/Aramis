package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.ui.command.FileCommand;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private ToolBar menu;

    @Injectable
    FileCommand fileCommand;

    @FXML
    void onNewButtonClick(Event e) {
        fileCommand.newFile(menu.getScene().getWindow());
    }

    @FXML
    void onOpenButtonClick(Event e) {
        fileCommand.openFile(menu.getScene().getWindow());
    }

    @FXML
    void onSaveButtonClick(Event e) {
        fileCommand.saveFile(menu.getScene().getWindow());
    }

    @FXML
    void onSaveAsButtonClick(Event e) {
        fileCommand.saveFileAs(menu.getScene().getWindow());
    }

    @FXML
    void onReloadButtonClick(Event e) {
        fileCommand.reloadFile();
    }

    @FXML
    void onFullScreenButtonClick(Event e) {
        Stage stage = (Stage) menu.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    void onZoomInButtonClick(Event e) {

    }

    @FXML
    void onZoomOutButtonClick(Event e) {

    }
}

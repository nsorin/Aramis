package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.ui.command.DisplayCommand;
import com.github.nsorin.aramis.ui.command.FileCommand;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;

public class MenuController {

    @FXML
    private ToolBar menu;

    @Injectable
    private FileCommand fileCommand;

    @Injectable
    private DisplayCommand displayCommand;

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
        displayCommand.toggleFullScreen();
    }

    @FXML
    void onZoomInButtonClick(Event e) {

    }

    @FXML
    void onZoomOutButtonClick(Event e) {

    }

    @FXML
    void onColorModeButtonClick(Event e) {

    }
}

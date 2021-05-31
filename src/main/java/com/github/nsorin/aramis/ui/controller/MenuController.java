package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.ui.command.FileCommand;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;

public class MenuController {

    @FXML
    private ToolBar menu;

    @Injectable
    FileCommand fileCommand;

    @FXML
    void onNewButtonClick(Event e) {
        fileCommand.newFile();
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
}

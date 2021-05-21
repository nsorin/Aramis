package com.github.nsorin.textn.ui.controller;

import com.github.nsorin.textn.injection.Injected;
import com.github.nsorin.textn.service.FileLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LayoutController {

    public TextArea textArea;

    private FileLoader fileLoader;

    @Injected
    public void setFileLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @FXML
    void onNewButtonClick(Event e) {
        textArea.setText("");
    }

    @FXML
    void onOpenButtonClick(Event e) {
        textArea.setText("temp");
    }

    @FXML
    void onSaveButtonClick(Event e) {
        textArea.setText("temp");
    }
}

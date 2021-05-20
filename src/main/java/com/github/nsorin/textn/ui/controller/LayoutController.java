package com.github.nsorin.textn.ui.controller;

import com.github.nsorin.textn.injection.Injected;
import com.github.nsorin.textn.service.Service;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LayoutController {

    public TextArea textArea;

    @Injected
    public Service service;

    @FXML
    void onNewButtonClick(Event e) {
        textArea.setText(service.getSomeText());
    }
}

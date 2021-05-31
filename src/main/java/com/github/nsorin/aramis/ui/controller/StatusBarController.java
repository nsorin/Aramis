package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.model.event.FilePropertiesUpdated;
import com.github.nsorin.aramis.model.event.SaveStatusUpdated;
import com.github.nsorin.aramis.observer.OnEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StatusBarController {
    @FXML
    private Text fileNameHolder;

    @FXML
    private Text saveStatusHolder;

    @OnEvent
    public void onFilePropertiesUpdated(FilePropertiesUpdated event) {
        fileNameHolder.setText(event.fileProperties().getName());
    }

    @OnEvent
    public void onSaveStatusUpdated(SaveStatusUpdated event) {
        if (event.saved()) {
            fileNameHolder.setStyle("-fx-font-style: normal;");
            saveStatusHolder.setStyle("-fx-font-style: normal;");
            saveStatusHolder.setText("saved");
        } else {
            fileNameHolder.setStyle("-fx-font-style: italic;");
            saveStatusHolder.setStyle("-fx-font-style: italic;");
            saveStatusHolder.setText("unsaved");
        }
    }
}

package com.github.nsorin.aramis.ui.service;

import javafx.scene.control.Alert;

public class AlertDispatcher implements AlertService {
    @Override
    public void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text);
        alert.getDialogPane().getScene().getStylesheets().add("style/alert.css");
        alert.show();
    }
}

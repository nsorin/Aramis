package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.event.TextContentUpdated;
import com.github.nsorin.aramis.observer.OnEvent;
import com.github.nsorin.aramis.ui.command.event.FocusInput;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class InputAreaController {

    @FXML
    private TextArea inputArea;

    @FXML
    public void initialize() {
        inputArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            applicationState.setSaved(false);
            applicationState.getTextContent().setContent(newValue);
            inputArea.requestFocus();
        });
    }

    @Injectable
    private ApplicationState applicationState;

    @OnEvent
    public void onTextContentUpdated(TextContentUpdated event) {
        inputArea.setText(event.textContent().getContent());
    }

    @OnEvent
    public void onFocusInput(FocusInput event) {
        inputArea.requestFocus();
    }
}